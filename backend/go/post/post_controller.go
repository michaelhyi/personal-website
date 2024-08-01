package post

import (
	"bytes"
	"encoding/json"
	"io"
	"net/http"

	"github.com/michaelhyi/personal-website/backend/go/auth"
)

type PostController struct {
	Service     *PostService
	AuthService *auth.AuthService
}

func (c *PostController) authorize(w http.ResponseWriter, r *http.Request) bool {
	authHeader := r.Header.Get("Authorization")
	err := c.AuthService.ValidateToken(&authHeader)

	if err != nil {
		w.WriteHeader(http.StatusUnauthorized)
		w.Write([]byte("Unauthorized"))
		return false
	}

	return true
}

func (c *PostController) CreatePost(w http.ResponseWriter, r *http.Request) {
	authorized := c.authorize(w, r)

	if !authorized {
		return
	}

	r.ParseMultipartForm(10 << 20)
	text := r.FormValue("text")
	file, _, err := r.FormFile("image")

	if err != nil {
		w.WriteHeader(http.StatusBadRequest)
		w.Write([]byte("Invalid request"))
		return
	}
	defer file.Close()

	var buf bytes.Buffer
	io.Copy(&buf, file)

	id, err := c.Service.CreatePost(&text, buf.Bytes())

	if err != nil {
		w.WriteHeader(http.StatusInternalServerError)
		w.Write([]byte(err.Error()))
		return
	}

	w.WriteHeader(http.StatusCreated)
	w.Write([]byte(id))
}

func (c *PostController) ReadPost(w http.ResponseWriter, r *http.Request) {
	id := r.PathValue("id")
	post, err := c.Service.ReadPost(&id)

	if err != nil {
		w.WriteHeader(http.StatusNotFound)
		w.Write([]byte("Post not found"))
		return
	}

	postJson, err := json.Marshal(post)

	if err != nil {
		w.WriteHeader(http.StatusInternalServerError)
		w.Write([]byte("Internal server error"))
		return
	}

	w.Header().Set("Content-Type", "application/json")
	w.WriteHeader(http.StatusOK)
	w.Write([]byte(postJson))
}

func (c *PostController) ReadPostImage(w http.ResponseWriter, r *http.Request) {
	id := r.PathValue("id")
	image, err := c.Service.ReadPostImage(&id)

	if err != nil {
		w.WriteHeader(http.StatusNotFound)
		w.Write([]byte(err.Error()))
		return
	}

	w.Header().Set("Content-Type", "image/jpeg")
	w.WriteHeader(http.StatusOK)
	w.Write(image)
}

func (c *PostController) ReadAllPosts(w http.ResponseWriter, r *http.Request) {
	posts, err := c.Service.ReadAllPosts()

	if err != nil {
		w.WriteHeader(http.StatusInternalServerError)
		w.Write([]byte("Internal server error"))
		return
	}

	postsJson, err := json.Marshal(posts)

	if err != nil {
		w.WriteHeader(http.StatusInternalServerError)
		w.Write([]byte("Internal server error"))
		return
	}

	w.Header().Set("Content-Type", "application/json")
	w.WriteHeader(http.StatusOK)
	w.Write([]byte(postsJson))
}

func (c *PostController) UpdatePost(w http.ResponseWriter, r *http.Request) {
	authorized := c.authorize(w, r)

	if !authorized {
		return
	}

	id := r.PathValue("id")

	r.ParseMultipartForm(10 << 20)
	text := r.FormValue("text")
	file, _, err := r.FormFile("image")

	var buf bytes.Buffer
	if err == nil {
		defer file.Close()
		io.Copy(&buf, file)
	}

	post, err := c.Service.UpdatePost(&id, &text, buf.Bytes())

	if err != nil {
		w.WriteHeader(http.StatusNotFound)
		w.Write([]byte(err.Error()))
		return
	}

	postJson, err := json.Marshal(post)

	if err != nil {
		w.WriteHeader(http.StatusInternalServerError)
		w.Write([]byte("Internal server error"))
		return
	}

	w.Header().Set("Content-Type", "application/json")
	w.WriteHeader(http.StatusOK)
	w.Write([]byte(postJson))
}

func (c *PostController) DeletePost(w http.ResponseWriter, r *http.Request) {
	authorized := c.authorize(w, r)

	if !authorized {
		return
	}

	id := r.PathValue("id")
	err := c.Service.DeletePost(&id)

	if err != nil {
		w.WriteHeader(http.StatusNotFound)
		w.Write([]byte(err.Error()))
		return
	}

	w.WriteHeader(http.StatusNoContent)
}
