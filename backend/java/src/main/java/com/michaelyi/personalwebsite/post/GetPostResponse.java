package com.michaelyi.personalwebsite.post;

import com.michaelyi.personalwebsite.util.HttpResponse;

public class GetPostResponse extends HttpResponse {
    private Post post;

    public GetPostResponse() {
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
