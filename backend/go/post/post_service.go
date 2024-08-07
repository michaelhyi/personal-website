package post

import (
	"bytes"
	"encoding/json"
	"fmt"

	"github.com/michaelhyi/personal-website/backend/go/cache"
	"github.com/michaelhyi/personal-website/backend/go/s3"
	"github.com/michaelhyi/personal-website/backend/go/util"
)

type PostService struct {
	Dao          *PostDao
	S3Service    *s3.S3Service
	CacheService *cache.CacheService
}

func (s *PostService) CreatePost(text *string, image []byte) (string, error) {
	post, err := ValidateAndConstructPost(text)

	if err != nil {
		return "", err
	}

	if image == nil || len(image) == 0 {
		return "", fmt.Errorf("Image is required")
	}

	_, err = s.GetPost(&post.ID)

	if err == nil {
		return "", fmt.Errorf("Post already exists")
	}

	s.Dao.CreatePost(&post)

	err = s.S3Service.PutObject(&post.ID, &image)

	if err != nil {
		return "", err
	}

	cacheVal, err := json.Marshal(post)

	if err != nil {
		return "", err
	}

	s.CacheService.Set(fmt.Sprintf("getPost?id=%s", post.ID), string(cacheVal))
	s.CacheService.Delete("getAllPosts")
	return post.ID, nil
}

func (s *PostService) GetPost(id *string) (Post, error) {
	var noop Post

	if util.IsStringInvalid(id) {
		return noop, fmt.Errorf("Invalid id")
	}

	b, err := s.CacheService.Get(fmt.Sprintf("getPost?id=%s", *id))

	if err == nil {
		var post Post
		json.Unmarshal([]byte(b), &post)
	}

	post, err := s.Dao.GetPost(id)

	if err == nil {
		var b []byte
		b, _ = json.Marshal(post)
		s.CacheService.Set(fmt.Sprintf("getPost?id=%s", *id), string(b))
		return post, nil
	}

	return noop, err
}

func (s *PostService) GetPostImage(id *string) ([]byte, error) {
	if util.IsStringInvalid(id) {
		return nil, fmt.Errorf("Invalid id")
	}

	_, err := s.GetPost(id)

	if err != nil {
		return nil, fmt.Errorf("Post not found")
	}

	cachedImg, err := s.CacheService.Get(fmt.Sprintf("getPostImage?id=%s", *id))

	if err == nil {
		return []byte(cachedImg), nil
	}

	image := s.S3Service.GetObject(id)

	if image == nil {
		return nil, fmt.Errorf("Post image not found")
	}

	s.CacheService.Set(fmt.Sprintf("getPostImage?id=%s", *id), string(image))

	return image, nil
}

func (s *PostService) GetAllPosts() ([]Post, error) {
	cachedPosts, err := s.CacheService.Get("getAllPosts")

	var posts []Post
	err = json.Unmarshal([]byte(cachedPosts), &posts)

	if err == nil {
		return posts, nil
	}

	posts, err = s.Dao.GetAllPosts()

	if err != nil {
		return nil, err
	}

	cacheVal, err := json.Marshal(posts)

	if err != nil {
		return nil, err
	}

	s.CacheService.Set("getAllPosts", string(cacheVal))

	return posts, nil
}

func (s *PostService) UpdatePost(id *string, text *string, image []byte) (Post, error) {
	var noop Post
	if util.IsStringInvalid(id) {
		return noop, fmt.Errorf("Invalid id")
	}

	post, err := s.GetPost(id)

	if err != nil {
		return noop, err
	}

	updatedPost, err := ValidateAndConstructPost(text)

	if err != nil {
		return noop, err
	}

	post.Title = updatedPost.Title
	post.Content = updatedPost.Content
	s.Dao.UpdatePost(&post)

	cacheVal, err := json.Marshal(post)

	if err != nil {
		return noop, err
	}

	s.CacheService.Set(fmt.Sprintf("getPost?id=%s", post.ID), string(cacheVal))
	s.CacheService.Delete("getAllPosts")

	if image == nil || len(image) <= 0 {
		return post, nil
	}

	currImage, err := s.GetPostImage(id)

	if err != nil {
		return noop, err
	}

	if image != nil && len(image) > 0 && !bytes.Equal(image, currImage) {
		s.S3Service.DeleteObject(id)
		s.S3Service.PutObject(id, &image)

		s.CacheService.Set(fmt.Sprintf("getPostImage?id=%s", *id), string(image))
	}

	return post, nil
}

func (s *PostService) DeletePost(id *string) error {
	if util.IsStringInvalid(id) {
		return fmt.Errorf("Invalid id")
	}

	_, err := s.GetPost(id)

	if err != nil {
		return err
	}

	s.S3Service.DeleteObject(id)
	s.Dao.DeletePost(id)

	s.CacheService.Delete(fmt.Sprintf("getPost?id=%s", *id))
	s.CacheService.Delete(fmt.Sprintf("getPostImage?id=%s", *id))
	s.CacheService.Delete("getAllPosts")

	return nil
}
