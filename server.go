package main

import (
	"log"
	"net/http"
)

func main() {
	pages := map[string]string{
		"/":          "./index.html",
		"/about":     "./pages/about.html",
		"/lauren":    "./pages/lauren.html",
		"/portfolio": "./pages/portfolio.html",
	}

	for route, file := range pages {
		f := file

		http.HandleFunc(route, func(w http.ResponseWriter, r *http.Request) {
			http.ServeFile(w, r, f)
		})
	}

	fs := http.FileServer(http.Dir("./static"))
	http.Handle("/static/", http.StripPrefix("/static/", fs))

	log.Printf("Server started on port 3000")
	err := http.ListenAndServe(":3000", nil)

	if err != nil {
		log.Fatal(err)
	}
}
