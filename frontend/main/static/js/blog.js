import { getAllPosts } from "./post-service.js";

document.getElementById("blog-section").innerHTML = await fetch("/static/html/loading.html").then(res => res.text());

try {
    const data = await getAllPosts();

    const html = data.map(post => `
        <a class="blog-post-anchor" href="/blog/${post.id}">
            ${post.title}
            <span class="blog-post-arrow">&#8599;</span>
        </a>`)
        .join("");

    document.getElementById("blog-section").innerHTML = html;
} catch (e) {
    document.getElementById("blog-section").innerHTML = await fetch("/static/html/not-found.html").then(res => res.text());
}