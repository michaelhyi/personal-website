package com.michaelhyi.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Post {
    @Id
    private String id;

    @CreationTimestamp
    private Date date;

    @Column(unique = true)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    public Post(String id,
                String title,
                String content) {
        this.id = id;
        this.title = title;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}