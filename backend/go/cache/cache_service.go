package cache

import (
	"context"
	"fmt"
	"time"

	"github.com/michaelhyi/personal-website/backend/go/util"
	"github.com/redis/go-redis/v9"
)

type CacheService struct {
	Rdb *redis.Client
}

var CACHE_TTL = 900000

func (s *CacheService) Get(key string) (string, error) {
	if util.IsStringInvalid(&key) {
		return "", fmt.Errorf("id is invalid")
	}

	value, err := s.Rdb.Get(context.Background(), key).Result()

	if err != nil {
		return "", err
	}

	return value, nil
}

func (s *CacheService) Set(key string, value string) error {
	if util.IsStringInvalid(&key) || util.IsStringInvalid(&value) {
		return fmt.Errorf("key or value is invalid")
	}

	s.Rdb.Set(context.Background(), key, value, time.Duration(CACHE_TTL)*time.Millisecond)
	return nil
}

func (s *CacheService) Delete(key string) error {
	if util.IsStringInvalid(&key) {
		return fmt.Errorf("key is invalid")
	}

	s.Rdb.Del(context.Background(), key)
	return nil
}
