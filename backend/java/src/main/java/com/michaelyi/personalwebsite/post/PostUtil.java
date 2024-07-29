package com.michaelyi.personalwebsite.post;

import com.michaelyi.personalwebsite.util.StringUtil;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public class PostUtil {
    public static final int CLOSING_H1_TAG_LENGTH = 5;
    public static final int OPENING_H1_TAG_LENGTH = 4;

    public static Post validateAndConstructPost(String text) {
        if (StringUtil.isStringInvalid(text)) {
            throw new IllegalArgumentException("Text is invalid");
        }

        int titleIndex = text.indexOf("</h1>");

        if (titleIndex == -1) {
            throw new IllegalArgumentException("Title cannot be blank");
        }

        String newTitle = text.substring(
                PostUtil.OPENING_H1_TAG_LENGTH,
                titleIndex
        );
        String newContent = text.substring(
                titleIndex + PostUtil.CLOSING_H1_TAG_LENGTH
        );

        if (StringUtil.isStringInvalid(newTitle)) {
            throw new IllegalArgumentException("Title cannot be blank");
        }

        if (StringUtil.isStringInvalid(newContent)) {
            throw new IllegalArgumentException("Content cannot be blank");
        }

        String newId = newTitle.toLowerCase()
                .replace(" ", "-")
                .replaceAll("[^a-z\\-]", "");

        String id = newId.charAt(newId.length() - 1) == '-'
                ? newId.substring(0, newId.length() - 1) : newId;
        Date date = new Date();
        String title = newTitle.replaceAll("<[^>]*>", "");
        String content = newContent;

        return new Post(id, date, title, content);
    }

    public static boolean isImageInvalid(MultipartFile image) {
        return image == null
                || image.isEmpty()
                || image.getSize() == 0
                || image.getOriginalFilename() == null
                || image.getOriginalFilename().isEmpty()
                || image.getOriginalFilename().isBlank()
                || !image.getOriginalFilename()
                        .matches(".*\\.(jpg|jpeg|png|webp)$")
                || !image.getContentType().matches("image/(jpeg|png|webp)");
    }
}
