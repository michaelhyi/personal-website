package s3

import (
	"bytes"
	"context"
	"io"
	"log"

	"github.com/aws/aws-sdk-go-v2/service/s3"
)

type S3Service struct {
	Bucket string
	Svc    *s3.Client
}

func (s *S3Service) PutObject(key *string, file *[]byte) error {
	_, err := s.Svc.PutObject(context.TODO(), &s3.PutObjectInput{
		Bucket: &s.Bucket,
		Key:    key,
		Body:   bytes.NewReader(*file),
	})

	if err != nil {
		return err
	}

	return nil
}

func (s *S3Service) GetObject(key *string) []byte {
	res, err := s.Svc.GetObject(context.TODO(), &s3.GetObjectInput{
		Bucket: &s.Bucket,
		Key:    key,
	})

	if err != nil {
		return nil
	}

	body, err := io.ReadAll(res.Body)
	if err != nil {
		return nil
	}

	return body
}

func (s *S3Service) DeleteObject(key *string) {
	_, err := s.Svc.DeleteObject(context.TODO(), &s3.DeleteObjectInput{
		Bucket: &s.Bucket,
		Key:    key,
	})

	if err != nil {
		log.Fatal(err)
	}
}
