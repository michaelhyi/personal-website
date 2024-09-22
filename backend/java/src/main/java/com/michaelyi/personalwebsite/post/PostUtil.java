package com.michaelyi.personalwebsite.post;

import com.michaelyi.personalwebsite.util.StringUtil;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public class PostUtil {
        public static final int OPENING_H1_TAG_LENGTH = "<h1>".length();
        public static final int CLOSING_H1_TAG_LENGTH = "</h1>".length();

        public static Post constructPost(String text) {
                if (!StringUtil.isStringValid(text)) {
                        throw new IllegalArgumentException("Text is invalid");
                }

                int openingH1TagIndex = text.indexOf("<h1>");
                int closingH1TagIndex = text.indexOf("</h1>");

                if (openingH1TagIndex == -1 || closingH1TagIndex == -1) {
                        throw new IllegalArgumentException("Title cannot be blank");
                }

                String title = text.substring(OPENING_H1_TAG_LENGTH, closingH1TagIndex);
                String content = text.substring(
                        closingH1TagIndex + CLOSING_H1_TAG_LENGTH);

                if (!StringUtil.isStringValid(title)) {
                        throw new IllegalArgumentException("Title cannot be blank");
                }

                if (!StringUtil.isStringValid(content)) {
                        throw new IllegalArgumentException("Content cannot be blank");
                }

                String id = convertTitleToId(title);
                Date date = new Date();
                title = removeHtmlTags(title);

                return new Post(id, date, title, null, content);
        }

        private static String convertTitleToId(String title) {
                // replace spaces with hyphens & remove all non-alphabetic characters
                String id = title.toLowerCase()
                        .replace(" ", "-")
                        .replaceAll("[^a-z\\-]", "");

                // remove trailing hyphens
                if (id.charAt(id.length() - 1) == '-') {
                        id = id.substring(0, id.length() - 1);
                }

                return id;
        }

        private static String removeHtmlTags(String s) {
                return s.replaceAll("<[^>]*>", "");
        }

        public static byte[] getImage(MultipartFile image) {
                if (!isImageValid(image)) {
                        throw new IllegalArgumentException("Image is invalid");
                }

                byte[] res;

                try {
                        res = image.getBytes();
                } catch (Exception e) {
                        res = null;
                }

                return res;
        }

        private static boolean isImageValid(MultipartFile image) {
                return image != null
                        && !image.isEmpty()
                        && image.getSize() != 0
                        && StringUtil.isStringValid(
                        image.getOriginalFilename())
                        && image
                        .getOriginalFilename()
                        .matches(".*\\.(jpg|jpeg|png|webp)")
                        && image
                        .getContentType()
                        .matches("image/(jpeg|png|webp)");
        }
}
