package com.michaelyi.personalwebsite.post;

import com.michaelyi.personalwebsite.util.HttpResponse;

import java.util.List;

public class GetAllPostsResponse extends HttpResponse {
    private List<Post> posts;

    public GetAllPostsResponse() {
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
