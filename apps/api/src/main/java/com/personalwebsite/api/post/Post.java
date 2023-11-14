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

    private String title;
    private int rating;
    private String image;
    private String description;

    @Column(columnDefinition = "TEXT")
    private String body;

    public Post(String title,
                int rating,
                String image,
                String description,
                String body) {
        this.title = title;
        this.rating = rating;
        this.image = image;
        this.description = description;
        this.body = body;
    }

    public Post() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

