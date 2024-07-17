package com.michaelyi.post;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import static com.michaelyi.util.Constants.CLOSING_H1_TAG_LENGTH;
import static com.michaelyi.util.Constants.OPENING_H1_TAG_LENGTH;
import static lombok.AccessLevel.NONE;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @Setter(NONE)
    private String id;

    @Setter(NONE)
    @Column(nullable = false, updatable = false)
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
}
