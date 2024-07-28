package com.michaelyi.personalwebsite.post;

import com.michaelyi.personalwebsite.s3.S3Service;
import com.michaelyi.personalwebsite.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PostService {
    private final PostDao dao;
    private final S3Service s3Service;

    public PostService(PostDao dao, S3Service s3Service) {
        this.dao = dao;
        this.s3Service = s3Service;
    }

    public String createPost(
            String text,
            MultipartFile image
    ) {
        Post post = PostUtil.validateAndConstructPost(text);

        if (PostUtil.isImageValid(image)) {
            throw new IllegalArgumentException("Image is invalid");
        }

        try {
            readPost(post.getId());

            throw new IllegalArgumentException(
                    "A post with the same title already exists"
            );
        } catch (NoSuchElementException e) {
            dao.createPost(post);
            String id = post.getId();

            try {
                s3Service.putObject(id, image.getBytes());
            } catch (IOException err) {
                throw new IllegalArgumentException("Image could not be read");
            }

            return id;
        }
    }

    public Post readPost(String id)
            throws NoSuchElementException {
        if (StringUtil.isStringInvalid(id)) {
            throw new IllegalArgumentException("Id cannot be empty");
        }

        return dao
                .readPost(id)
                .orElseThrow(() ->
                        new NoSuchElementException("Post not found"));
    }

    public byte[] readPostImage(String id) {
        if (StringUtil.isStringInvalid(id)) {
            throw new IllegalArgumentException("Id cannot be empty");
        }

        readPost(id);

        try {
            return s3Service.getObject(id);
        } catch (NoSuchKeyException | NoSuchElementException err) {
            throw new NoSuchElementException("Post image not found");
        }
    }

    public List<Post> readAllPosts() {
        return dao.readAllPosts();
    }

    public Post updatePost(
            String id,
            String text,
            MultipartFile image
    ) {
        if (StringUtil.isStringInvalid(id)) {
            throw new IllegalArgumentException("Id cannot be empty");
        }

        Post post = readPost(id);
        Post updatedPost = PostUtil.validateAndConstructPost(text);

        if (PostUtil.isImageValid(image)) {
            throw new IllegalArgumentException("Image is invalid");
        }

        post.setTitle(updatedPost.getTitle());
        post.setContent(updatedPost.getContent());
        dao.updatePost(post);

        byte[] currentImage = readPostImage(id);
        byte[] newImage;

        try {
            newImage = image.getBytes();
        } catch (NullPointerException | IOException e) {
            newImage = null;
        }

        if (newImage != null && !Arrays.equals(currentImage, newImage)) {
            s3Service.deleteObject(id);
            s3Service.putObject(id, newImage);
        }

        return post;
    }

    public void deletePost(String id)
            throws NoSuchElementException {
        if (StringUtil.isStringInvalid(id)) {
            throw new IllegalArgumentException("Id cannot be empty");
        }

        readPost(id);

        s3Service.deleteObject(id);
        dao.deletePost(id);
    }
}
