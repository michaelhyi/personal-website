package auth

import (
	"fmt"
	"strings"
	"time"

	"github.com/golang-jwt/jwt/v5"
	"github.com/michaelhyi/personal-website/backend/go/util"
	"golang.org/x/crypto/bcrypt"
)

type AuthService struct {
	AdminPassword string
	SigningKey    string
}

var BEARER_PREFIX_LENGTH = 7
var EXPIRATION = 6048000000
var ADMIN_EMAIL = "admin@michael-yi.com"

func (s *AuthService) Login(req *AuthRequest) (string, error) {
	if req == nil {
		return "", fmt.Errorf("Request cannot be null")
	}

	if util.IsStringInvalid(&req.Password) {
		return "", fmt.Errorf("Password cannot be empty")
	}

	err := bcrypt.CompareHashAndPassword([]byte(s.AdminPassword), []byte(req.Password))

	if err != nil {
		return "", fmt.Errorf("Unauthorized")
	}

	claims := jwt.MapClaims{
		"sub": ADMIN_EMAIL,
		"iat": time.Now().UnixMilli(),
		"exp": time.Now().UnixMilli() + int64(EXPIRATION),
	}
	token := jwt.NewWithClaims(jwt.SigningMethodHS256, claims)

	tokenString, err := token.SignedString([]byte(s.SigningKey))

	if err != nil {
		return "", fmt.Errorf("Internal server error")
	}

	return tokenString, nil
}

func (s *AuthService) ValidateToken(authHeader *string) error {
	if util.IsStringInvalid(authHeader) || !strings.HasPrefix(*authHeader, "Bearer ") {
		return fmt.Errorf("Authorization header is invalid")
	}

	tokenString := (*authHeader)[BEARER_PREFIX_LENGTH:]
	token, err := jwt.Parse(tokenString, func(token *jwt.Token) (interface{}, error) {
		if _, ok := token.Method.(*jwt.SigningMethodHMAC); !ok {
			return nil, fmt.Errorf("Unexpected signing method: %v", token.Header["alg"])
		}

		return []byte(s.SigningKey), nil
	})

	if err != nil {
		return err
	}

	claims, ok := token.Claims.(jwt.MapClaims)

	if !ok {
		return fmt.Errorf("Unauthorized")
	}

	if claims["sub"] != ADMIN_EMAIL || claims["exp"].(float64) < float64(time.Now().UnixMilli()) {
		return fmt.Errorf("Unauthorized")
	}

	return nil
}
