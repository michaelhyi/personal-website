import { loading } from "./loading.js";
import { notFound } from "./not-found.js";
import { getAllPosts } from "./post-service.js";

let data = null;
let error = null;

document.getElementById("blog-section").innerHTML = loading;

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

    document.getElementById("blog-section").innerHTML = notFound;
}