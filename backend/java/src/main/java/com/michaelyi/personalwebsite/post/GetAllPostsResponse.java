package com.michaelyi.personalwebsite.post;

import java.util.List;

import com.michaelyi.personalwebsite.util.HttpResponse;

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
