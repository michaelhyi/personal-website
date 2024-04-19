package com.michaelyi.post;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Post implements Serializable {
    @Id
    @Column(
            nullable = false,
            unique = true
    )
    private String id;

    @CreationTimestamp
    @Column(nullable = false)
    private Date date;

    @Column(
            nullable = false,
            unique = true
    )
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

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

        String newTitle = text.substring(4, titleIndex);
        String newContent = text.substring(titleIndex + 5);

        if (newTitle.isBlank() || newTitle.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be blank.");
        }

        if (newContent.isBlank() || newContent.isEmpty()) {
            throw new IllegalArgumentException("Content cannot be blank.");
        }

        boolean containsYear = newTitle.contains("(")
                && newTitle.contains(")")
                && newTitle.indexOf(")")
                - newTitle.indexOf("(") == 5;

        if (!containsYear) {
            throw new IllegalArgumentException(
                    "Title must contain a year in parentheses.");
        }

        if (newTitle.contains("(")
                && !newTitle.substring(
                newTitle.indexOf("(") - 1,
                newTitle.indexOf("(")
        ).equals(" ")) {
            throw new IllegalArgumentException(
                    "Year must be preceded by a space.");
        }

        String newId = newTitle.toLowerCase()
                .replace(" ", "-")
                .replaceAll("[^a-z\\-]", "");

        id = newId.charAt(newId.length() - 1) == '-'
                ? newId.substring(0, newId.length() - 1) : newId;
        title = newTitle.replaceAll("<[^>]*>", "");
        content = newContent;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
