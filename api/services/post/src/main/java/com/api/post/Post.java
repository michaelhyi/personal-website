package com.api.post;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Post {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private Date date;
    private String body;

    public Post(String title, Date date, String body) {
        this.title = title;
        this.date = date;
        this.body = body;
    }

    public Post() {}

    public Long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
