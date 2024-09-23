package com.michaelyi.personalwebsite.post;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Arrays;
import java.util.Date;

@Entity
public class Post {
    @Id
    private String id;

    @Column(nullable = false, updatable = false)
    private Date date;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false, columnDefinition = "BLOB")
    private byte[] image;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    public Post(String id, Date date, String title, byte[] image, String content) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.image = image;
        this.content = content;
    }

    public Post() {
    }

    public String getId() {
        return id;
    }

    public Date getDate() {
        return date;
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
                && date.equals(other.date)
                && title.equals(other.title)
                && Arrays.equals(image, other.image)
                && content.equals(other.content);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        int prime = 31;

        hash = hash * prime + id.hashCode();
        hash = hash * prime + (date != null ? date.hashCode() : 0);
        hash = hash * prime + (title != null ? title.hashCode() : 0);
        hash = hash * prime + (image != null ? Arrays.hashCode(image) : 0);
        hash = hash * prime + (content != null ? content.hashCode() : 0);

        return hash;
    }
}
