package main

import (
	"io"
	"log"
	"net/http"
	"os"
)

func main() {
	pages := map[string]string{
		"/":          "./pages/index.html",
		"/about":     "./pages/about.html",
		"/blog":      "./pages/blog.html",
		"/blog/":     "./pages/read-post.html",
		"/lauren":    "./pages/lauren.html",
		"/portfolio": "./pages/portfolio.html",
	}

	for route, file := range pages {
		http.HandleFunc(route, func(w http.ResponseWriter, r *http.Request) {
			http.ServeFile(w, r, file)
		})
	}

	fs := http.FileServer(http.Dir("./static"))
	http.Handle("/static/", http.StripPrefix("/static/", fs))

	http.HandleFunc("/api/", func(w http.ResponseWriter, r *http.Request) {
		url := os.Getenv("API_URL") + r.URL.Path[len("/api"):]
		req, err := http.NewRequest(r.Method, url, r.Body)

		if err != nil {
			w.WriteHeader(http.StatusInternalServerError)
			w.Write([]byte(err.Error()))
			return
		}

		req.Header = r.Header

		client := &http.Client{}
		res, err := client.Do(req)

		if err != nil {
			w.WriteHeader(http.StatusInternalServerError)
			w.Write([]byte(err.Error()))
			return
		}

		defer res.Body.Close()

		for key, values := range res.Header {
			for _, value := range values {
				w.Header().Add(key, value)
			}
		}

		w.WriteHeader(res.StatusCode)

		_, err = io.Copy(w, res.Body)
		if err != nil {
			http.Error(w, "Error copying response body", http.StatusInternalServerError)
			return
		}
	})

	log.Printf("Server started on port 3000")
	err := http.ListenAndServe(":3000", nil)

	if err != nil {
		log.Fatal(err)
	}
}
