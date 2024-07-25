package com.michaelyi.personalwebsite.post;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Date;

import static com.michaelyi.personalwebsite.util.Constants.CLOSING_H1_TAG_LENGTH;
import static com.michaelyi.personalwebsite.util.Constants.OPENING_H1_TAG_LENGTH;

@Entity
public class Post {
    @Id
    private String id;

    @Column(nullable = false, updatable = false)
    private Date date;

    @Column(
            nullable = false,
            unique = true
    )
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    public Post(String id, Date date, String title, String content) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.content = content;
    }

    public Post(String text) {
        if (text == null
                || text.isBlank()
                || text.isEmpty()) {
            throw new IllegalArgumentException("Fields cannot be blank.");
        }

        int titleIndex = text.indexOf("</h1>");

        if (titleIndex == -1) {
            throw new IllegalArgumentException("Title cannot be blank.");
        }

        String newTitle = text.substring(OPENING_H1_TAG_LENGTH, titleIndex);
        String newContent = text.substring(titleIndex + CLOSING_H1_TAG_LENGTH);

        if (newTitle.isBlank() || newTitle.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be blank.");
        }

        if (newContent.isBlank() || newContent.isEmpty()) {
            throw new IllegalArgumentException("Content cannot be blank.");
        }

        String newId = newTitle.toLowerCase()
                .replace(" ", "-")
                .replaceAll("[^a-z\\-]", "");

        id = newId.charAt(newId.length() - 1) == '-'
                ? newId.substring(0, newId.length() - 1) : newId;
        date = new Date();
        title = newTitle.replaceAll("<[^>]*>", "");
        content = newContent;
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

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
