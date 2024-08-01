package main

import (
	"context"
	"database/sql"
	"log"
	"os"
	"time"

	"github.com/aws/aws-sdk-go-v2/config"
	"github.com/aws/aws-sdk-go-v2/service/s3"
	"github.com/go-sql-driver/mysql"
	"github.com/michaelhyi/personal-website/backend/go/auth"
	"github.com/michaelhyi/personal-website/backend/go/cache"
	"github.com/michaelhyi/personal-website/backend/go/health"
	"github.com/michaelhyi/personal-website/backend/go/post"
	S3 "github.com/michaelhyi/personal-website/backend/go/s3"
	"github.com/redis/go-redis/v9"
)

func GetMysql() *sql.DB {
	cfg := mysql.Config{
		User:   "root",
		Passwd: "root",
		Net:    "tcp",
		Addr:   "localhost:3306",
		DBName: "personal_website_api",
	}

	db, err := sql.Open("mysql", cfg.FormatDSN())

	if err != nil {
		log.Fatal(err)
	}

	err = db.Ping()
	if err != nil {
		log.Fatal(err)
	}

	return db
}

func GetRedis() *redis.Client {
	return redis.NewClient(&redis.Options{
		Addr:     "localhost:6379",
		Password: "",
		DB:       0,
	})
}

func GetS3Service() S3.S3Service {
	cfg, err := config.LoadDefaultConfig(context.TODO(), config.WithRegion("us-west-2"))
	if err != nil {
		log.Fatal(err)
	}

	svc := s3.NewFromConfig(cfg)
	return S3.S3Service{
		Bucket: os.Getenv("PERSONAL_WEBSITE_AWS_S3_BUCKET"),
		Svc:    svc,
	}
}

func GetCacheService(rdb *redis.Client) cache.CacheService {
	return cache.CacheService{
		Rdb: rdb,
	}
}

func GetHealthController() health.HealthController {
	healthService := health.HealthService{
		StartTime: time.Now(),
	}

	return health.HealthController{
		Service: &healthService,
	}
}

func GetAuthServiceAndController() (auth.AuthService, auth.AuthController) {
	authService := auth.AuthService{
		AdminPassword: os.Getenv("PERSONAL_WEBSITE_SECURITY_AUTH_ADMIN_PW"),
		SigningKey:    os.Getenv("PERSONAL_WEBSITE_SECURITY_JWT_SECRET_KEY"),
	}

	authController := auth.AuthController{
		Service: &authService,
	}

	return authService, authController
}

func GetPostController(db *sql.DB, s3Service *S3.S3Service, cacheService *cache.CacheService, authService *auth.AuthService) post.PostController {
	postDao := post.PostDao{
		Db: db,
	}

	postService := post.PostService{
		Dao:          &postDao,
		S3Service:    s3Service,
		CacheService: cacheService,
	}

	return post.PostController{
		Service:     &postService,
		AuthService: authService,
	}
}
