package com.personalwebsite.api.post;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
public class Post {
    @Id
    @GeneratedValue
    private Long id;

    @CreationTimestamp
    private Date date;

    @Column(unique = true)
    private String title;
    private String image;

    @Column(columnDefinition = "TEXT")
    private String content;

    public Post(String title,
                String image,
                String content) {
        this.title = title;
        this.image = image;
        this.content = content;
    }

    public Post() {
    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

