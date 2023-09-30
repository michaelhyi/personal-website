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

    private String desc;

    @Column(columnDefinition = "TEXT")
    private String body;

    public Post(String title, String desc, String body) {
        this.title = title;
        this.desc = desc;
        this.body = body;
    }

    public Post() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getBody() {
        return body;
    }

    public Date getDate() {
        return date;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

