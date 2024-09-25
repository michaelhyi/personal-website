package com.michaelyi.personalwebsite.post;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Entity
public class Post {
    @Id
    private String id;

    @Column(columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private Date createdAt;

    @Column(columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date updatedAt;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL UNIQUE")
    private String title;

    @Column(columnDefinition = "MEDIUMBLOB NOT NULL")
    private byte[] image;

    @Column(columnDefinition = "TEXT NOT NULL")
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
        return Objects.equals(id, other.id)
                && Objects.equals(createdAt, other.createdAt)
                && Objects.equals(updatedAt, other.updatedAt)
                && Objects.equals(title, other.title)
                && Arrays.equals(image, other.image)
                && Objects.equals(content, other.content);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        int prime = 31;

        hash = hash * prime + Objects.hashCode(id);
        hash = hash * prime + Objects.hashCode(createdAt);
        hash = hash * prime + Objects.hashCode(updatedAt);
        hash = hash * prime + Objects.hashCode(title);
        hash = hash * prime + Arrays.hashCode(image);
        hash = hash * prime + Objects.hashCode(content);

        return hash;
    }
}
