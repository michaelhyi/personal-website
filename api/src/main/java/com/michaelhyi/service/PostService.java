package com.michaelhyi.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.michaelhyi.dao.PostRepository;
import com.michaelhyi.dto.PostRequest;
import com.michaelhyi.entity.Post;
import com.michaelhyi.exception.PostNotFoundException;
import com.michaelhyi.exception.S3Exception;

import lombok.AllArgsConstructor;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository repository;
    private final S3Service s3Service;

    public String createPost(PostRequest req) {
        PostDestructuredRequest postDestructuredRequest
                = destructureRequest(req);
        String title = postDestructuredRequest.title();
        String content = postDestructuredRequest.content();

        Optional<Post> post = repository.findById(req.id());

        if (post.isPresent()) {
            throw new IllegalArgumentException(
                    "A post with the same title already exists."
            );
        }

        Post newPost = Post.builder()
                            .id(req.id())
                            .title(title)
                            .content(content)
                            .build();

        repository.saveAndFlush(newPost);
        return newPost.getId();
    }

    public void createPostImage(String id, MultipartFile file) {
        try {
            if (readPostImage(id) != null) {
                s3Service.deleteObject(id);
            }
        } catch (PostNotFoundException e) {
            throw new PostNotFoundException();
        } catch (S3Exception e) {
            try {
                s3Service.putObject(id, file.getBytes());
            } catch (IOException ex) {
                throw new S3Exception();
            }
        }
    }

    public Post readPost(String id) {
        return repository
                .findById(id)
                .orElseThrow(PostNotFoundException::new);
    }

    public byte[] readPostImage(String id) {
        readPost(id);

        try {
            return s3Service.getObject(id);
        } catch (NoSuchKeyException e) {
            throw new S3Exception();
        }
    }

    public List<Post> readAllPosts() {
        return repository
                .findAll(Sort.by(Sort.Direction.DESC, "date"));
    }

    public void updatePost(String id, PostRequest req) {
        Post post = readPost(id);

        PostDestructuredRequest postDestructuredRequest
                = destructureRequest(req);
        String title = postDestructuredRequest.title();
        String content = postDestructuredRequest.content();

        post.setTitle(title);
        post.setContent(content);

        repository.save(post);
    }

    public void deletePost(String id) {
        readPost(id);

        s3Service.deleteObject(id);
        repository.deleteById(id);
    }

    private PostDestructuredRequest destructureRequest(PostRequest req) {
        if (req.text() == null
                || req.text().isBlank()
                || req.text().isEmpty()) {
            throw new IllegalArgumentException("Fields cannot be blank.");
        }

        String text = req.text();
        int titleIndex = text.indexOf("</h1>");

        if (titleIndex == -1) {
            throw new IllegalArgumentException("Title cannot be blank.");
        }

        String title = text.substring(4, titleIndex);
        String content = text.substring(titleIndex + 5);

        if (title.isBlank() || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be blank.");
        }

        if (content.isBlank() || content.isEmpty()) {
            throw new IllegalArgumentException("Content cannot be blank.");
        }

        return new PostDestructuredRequest(
                title.replaceAll("<[^>]*>", ""),
                content
        );
    }

    private record PostDestructuredRequest(String title, String content) {
    }
}
