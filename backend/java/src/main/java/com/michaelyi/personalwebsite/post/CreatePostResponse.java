package com.michaelyi.personalwebsite.post;

import com.michaelyi.personalwebsite.util.HttpResponse;

public class CreatePostResponse extends HttpResponse {
    private String postId;

    public CreatePostResponse() {
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
