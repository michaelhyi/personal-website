package main

import (
	"log"
	"net/http"
)

func getServeMux() *http.ServeMux {
	mux := http.NewServeMux()

	routes := map[string]string{
		"/":          "./index.html",
		"/about":     "./pages/about.html",
		"/lauren":    "./pages/lauren.html",
		"/portfolio": "./pages/portfolio.html",
	}

	mux.HandleFunc("/", func(w http.ResponseWriter, r *http.Request) {
		file, ok := routes[r.URL.Path]

		if ok {
			http.ServeFile(w, r, file)
		} else {
			http.NotFound(w, r)
		}
	})

	fs := http.FileServer(http.Dir("./assets"))
	mux.Handle("/assets/", http.StripPrefix("/assets/", fs))

	return mux
}

func main() {
	mux := getServeMux()

	log.Printf("server started on port 3000")
	err := http.ListenAndServe(":3000", mux)

	if err != nil {
		log.Fatal(err)
	}
}
