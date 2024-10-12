import { getAllPosts } from "./post-service.js";

let data = null;
let error = null;

document.getElementById("blog-section").innerHTML = await fetch("/static/html/loading.html").then(res => res.text());
document.getElementById("loading").innerHTML = await fetch("/static/html/spinner.html").then(res => res.text());

try {
    data = await getAllPosts();

    const postsHtml = data.map(post => `
        <a class="blog-post-anchor" href="/blog/${post.id}">
            ${post.title}
            <span class="blog-post-arrow">&#8599;</span>
        </a>`)
        .join("");

    document.getElementById("blog-section").innerHTML = postsHtml;
} catch (e) {
    error = e;

    document.getElementById("blog-section").innerHTML = await fetch("/static/html/not-found.html").then(res => res.text());
}