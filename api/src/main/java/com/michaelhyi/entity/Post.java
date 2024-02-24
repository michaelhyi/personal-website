package com.michaelhyi.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import com.michaelhyi.dto.PostRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @Setter(AccessLevel.NONE)
    @Column(
        nullable = false,
        unique = true,
        updatable = false
    )
    private String id;

    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    @Column(nullable = false, updatable = false)
    private Date date;

    @Setter(AccessLevel.NONE)
    @Column(
        nullable = false,
        unique = true,
        updatable = false
    )
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    public Post(PostRequest req) {
        if (req.text() == null
            || req.text().isBlank()
            || req.text().isEmpty()) {
            throw new IllegalArgumentException("Fields cannot be blank.");
        }

        String text = req.text();
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

        String newId = newTitle.toLowerCase()
                                .replace(" ", "-")
                                .replaceAll("[^a-z\\-]", "");

        id = newId.substring(0, newId.length() - 1);
        title = newTitle.replaceAll("<[^>]*>", "");
        content = newContent;
    }
}