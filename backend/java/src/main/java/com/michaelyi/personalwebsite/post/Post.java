package com.michaelyi.personalwebsite.post;

import java.util.Arrays;
import java.util.Date;

public class Post {
    private String id;
    private Date createdAt;
    private Date updatedAt;
    private String title;
    private byte[] image;
    private String content;

    public Post(String id,
                Date createdAt,
                Date updatedAt,
                String title,
                byte[] image,
                String content
    ) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.title = title;
        this.image = image;
        this.content = content;
    }

    public Post(String id, String title, String content) {
        this(id, null, null, title, null, content);
    }

    public Post() {
    }

    public String getId() {
        return id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public String getTitle() {
        return title;
    }

    public byte[] getImage() {
        return image;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final Post other = (Post) obj;
        return id.equals(other.id)
                && createdAt.equals(other.createdAt)
                && updatedAt.equals(other.updatedAt)
                && title.equals(other.title)
                && Arrays.equals(image, other.image)
                && content.equals(other.content);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        int prime = 31;

        hash = hash * prime + id.hashCode();
        hash = hash * prime + (createdAt != null ? createdAt.hashCode() : 0);
        hash = hash * prime + (updatedAt != null ? updatedAt.hashCode() : 0);
        hash = hash * prime + (title != null ? title.hashCode() : 0);
        hash = hash * prime + (image != null ? Arrays.hashCode(image) : 0);
        hash = hash * prime + (content != null ? content.hashCode() : 0);

        return hash;
    }
}
