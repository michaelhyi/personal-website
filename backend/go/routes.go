package main

import (
	"net/http"

	"github.com/michaelhyi/personal-website/backend/go/auth"
	"github.com/michaelhyi/personal-website/backend/go/health"
	"github.com/michaelhyi/personal-website/backend/go/post"
)

func SetRoutes(healthController *health.HealthController, authController *auth.AuthController, postController *post.PostController) {
	http.HandleFunc("GET /", healthController.Check)

	http.HandleFunc("POST /v2/auth/login", authController.Login)
	http.HandleFunc("GET /v2/auth/validate-token", authController.ValidateToken)

	http.HandleFunc("POST /v2/post", postController.CreatePost)
	http.HandleFunc("GET /v2/post/{id}", postController.ReadPost)
	http.HandleFunc("GET /v2/post/image/{id}", postController.ReadPostImage)
	http.HandleFunc("GET /v2/post", postController.ReadAllPosts)
	http.HandleFunc("PUT /v2/post/{id}", postController.UpdatePost)
	http.HandleFunc("DELETE /v2/post/{id}", postController.DeletePost)
}
