package post

import (
	"errors"
	"regexp"
	"strings"
	"time"

	"github.com/michaelhyi/personal-website/backend/go/util"
)

var CLOSING_H1_TAG_LENGTH = 5
var OPENING_H1_TAG_LENGTH = 4

func ValidateAndConstructPost(text *string) (Post, error) {
	var noop Post

	if util.IsStringInvalid(text) {
		return noop, errors.New("Text is invalid")
	}

	titleIndex := strings.Index(*text, "</h1>")

	if titleIndex == -1 {
		return noop, errors.New("Title cannot be blank")
	}

	newTitle := (*text)[OPENING_H1_TAG_LENGTH:titleIndex]
	newContent := (*text)[titleIndex+CLOSING_H1_TAG_LENGTH:]

	if util.IsStringInvalid(&newTitle) {
		return noop, errors.New("Title cannot be blank")
	}

	if util.IsStringInvalid(&newContent) {
		return noop, errors.New("Content cannot be blank")
	}

	newId := strings.ToLower(newTitle)
	newId = strings.ReplaceAll(
		strings.ToLower(newTitle),
		" ",
		"-",
	)

	regex := regexp.MustCompile("[^a-z-]")
	newId = regex.ReplaceAllString(newId, "")

	var id string
	if newId[len(newId)-1] == '-' {
		id = newId[:len(newId)-1]
	} else {
		id = newId
	}

	date := time.Now().Format("2006-01-02 15:04:05")
	title := strings.ReplaceAll(newTitle, "<[^>]*>", "")
	content := newContent

	post := Post{
		ID:      id,
		Date:    date,
		Title:   title,
		Content: content,
	}

	return post, nil
}

//
//    public static boolean isImageInvalid(MultipartFile image) {
//        return image == null
//                || image.isEmpty()
//                || image.getSize() == 0
//                || image.getOriginalFilename() == null
//                || image.getOriginalFilename().isEmpty()
//                || image.getOriginalFilename().isBlank()
//                || !image.getOriginalFilename()
//                        .matches(".*\\.(jpg|jpeg|png|webp)$")
//                || !image.getContentType().matches("image/(jpeg|png|webp)");
//    }
