package com.michaelyi.personalwebsite.post;

import com.michaelyi.personalwebsite.util.HttpResponse;

public class GetPostImageResponse extends HttpResponse {
    private byte[] image;

    public GetPostImageResponse() {
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
