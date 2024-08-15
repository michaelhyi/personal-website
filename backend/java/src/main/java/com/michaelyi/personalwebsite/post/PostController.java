package com.michaelyi.personalwebsite.post;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.michaelyi.personalwebsite.util.HttpResponse;

@RestController
@RequestMapping("/v2/post")
public class PostController {
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CreatePostResponse> createPost(
            @RequestParam("text") String text,
            @RequestParam("image") MultipartFile image) {
        CreatePostResponse res = new CreatePostResponse();

        try {
            String postId = service.createPost(text, image);
            res.setPostId(postId);
            res.setHttpStatus(HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            res.setError(e.getMessage());
            res.setHttpStatus(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            res.setError("Internal server error");
            res.setHttpStatus(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(res, res.getHttpStatus());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPostResponse> getPost(@PathVariable String id) {
        GetPostResponse res = new GetPostResponse();
        Post post = null;

        try {
            post = service.getPost(id);
            res.setPost(post);
            res.setHttpStatus(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            res.setError(e.getMessage());
            res.setHttpStatus(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            res.setError("Internal server error");
            res.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (post == null) {
            res.setError("Post not found");
            res.setHttpStatus(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(res, res.getHttpStatus());
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<GetPostImageResponse> getPostImage(
            @PathVariable String id) {
        GetPostImageResponse res = new GetPostImageResponse();

        try {
            byte[] image = service.getPostImage(id);
            res.setImage(image);
            res.setHttpStatus(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            res.setError(e.getMessage());
            res.setHttpStatus(HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            res.setError(e.getMessage());
            res.setHttpStatus(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            res.setError("Internal server error");
            res.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(res, res.getHttpStatus());
    }

    @GetMapping
    public ResponseEntity<GetAllPostsResponse> getAllPosts() {
        GetAllPostsResponse res = new GetAllPostsResponse();

        try {
            List<Post> posts = service.getAllPosts();
            res.setPosts(posts);
            res.setHttpStatus(HttpStatus.OK);
        } catch (Exception e) {
            res.setError("Internal server error");
            res.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(res, res.getHttpStatus());
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpResponse> updatePost(
            @PathVariable String id,
            @RequestParam("text") String text,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        HttpResponse res = new HttpResponse();

        try {
            service.updatePost(id, text, image);
            res.setHttpStatus(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            res.setError(e.getMessage());
            res.setHttpStatus(HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            res.setError(e.getMessage());
            res.setHttpStatus(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            res.setError("Internal server error");
            res.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(res, res.getHttpStatus());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpResponse> deletePost(@PathVariable String id) {
        HttpResponse res = new HttpResponse();

        try {
            service.deletePost(id);
            res.setHttpStatus(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            res.setError(e.getMessage());
            res.setHttpStatus(HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            res.setError(e.getMessage());
            res.setHttpStatus(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            res.setError("Internal server error");
            res.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(res, res.getHttpStatus());
    }
}
