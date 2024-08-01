package main

import (
	"log"

	"net/http"
)

func main() {
	db := GetMysql()
	rdb := GetRedis()

	s3Service := GetS3Service()
	cacheService := GetCacheService(rdb)

	healthController := GetHealthController()
	authService, authController := GetAuthServiceAndController()
	postController := GetPostController(db, &s3Service, &cacheService, &authService)

	SetRoutes(&healthController, &authController, &postController)

	log.Println("Server listening on port 8080")
	http.ListenAndServe(":8080", nil)
}
