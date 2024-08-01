package auth

import (
	"encoding/json"
	"io"
	"net/http"
)

type AuthController struct {
	Service *AuthService
}

func (c *AuthController) Login(w http.ResponseWriter, r *http.Request) {
	b, err := io.ReadAll(r.Body)

	if err != nil {
		w.WriteHeader(http.StatusBadRequest)
		w.Write([]byte("Invalid request"))
		return
	}

	var req AuthRequest
	err = json.Unmarshal(b, &req)

	if err != nil {
		w.WriteHeader(http.StatusBadRequest)
		w.Write([]byte("Invalid request"))
		return
	}

	token, err := c.Service.Login(&req)

	if err != nil {
		w.WriteHeader(http.StatusUnauthorized)
		w.Write([]byte("Unauthorized"))
		return
	}

	w.WriteHeader(http.StatusOK)
	w.Write([]byte(token))
}

func (c *AuthController) ValidateToken(w http.ResponseWriter, r *http.Request) {
	authHeader := r.Header.Get("Authorization")

	err := c.Service.ValidateToken(&authHeader)

	if err != nil {
		w.WriteHeader(http.StatusUnauthorized)
		w.Write([]byte("Unauthorized"))
		return
	}

	w.WriteHeader(http.StatusOK)
}
