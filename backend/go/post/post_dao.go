package post

import (
	"database/sql"
	"fmt"
)

type PostDao struct {
	Db *sql.DB
}

func (d *PostDao) CreatePost(post *Post) error {
	sql := "INSERT INTO post (id, date, title, content) VALUES (?, ?, ?, ?)"
	_, err := d.Db.Exec(sql, post.ID, post.Date, post.Title, post.Content)
	return err
}

func (d *PostDao) GetPost(id *string) (Post, error) {
	sql := "SELECT * FROM post WHERE id = ? LIMIT 1"
	rows, err := d.Db.Query(sql, id)

	var noop Post
	if err != nil {
		return noop, err
	}

	defer rows.Close()
	var post Post

	nextRow := rows.Next()

	if !nextRow {
		return noop, fmt.Errorf("Post not found")
	}

	if err := rows.Scan(&post.Date, &post.Content, &post.ID, &post.Title); err != nil {
		return noop, err
	}

	if err := rows.Err(); err != nil {
		return noop, err
	}

	return post, nil
}

func (d *PostDao) GetAllPosts() ([]Post, error) {
	sql := "SELECT * FROM post ORDER BY date DESC"
	rows, err := d.Db.Query(sql)

	var noop []Post
	if err != nil {
		return noop, err
	}

	var posts []Post

	defer rows.Close()
	for rows.Next() {
		var post Post

		if err := rows.Scan(&post.Date, &post.Content, &post.ID, &post.Title); err != nil {
			return noop, err
		}

		posts = append(posts, post)
	}

	if err := rows.Err(); err != nil {
		return noop, err
	}

	return posts, nil
}

func (d *PostDao) UpdatePost(post *Post) error {
	sql := "UPDATE post SET title = ?, content = ? WHERE id = ?"
	_, err := d.Db.Exec(sql, post.Title, post.Content, post.ID)
	return err
}

func (d *PostDao) DeletePost(id *string) error {
	sql := "DELETE FROM post WHERE id = ?"
	_, err := d.Db.Exec(sql, id)
	return err
}
