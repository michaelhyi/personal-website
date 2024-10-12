import format from "./date-util.js"
import { getPost } from "./post-service.js";

let data = null;
let error = null;

document.getElementById("content").innerHTML = await fetch("/static/html/loading.html").then(res => res.text());

try {
    id = window.location.pathname.split("/").pop();
    data = await getPost();

    document.getElementById("content").innerHTML = `
        <a href="/blog" class="back-arrow">&#8592;</a>
        <h1 class="post-title">${data.post.title}</h1>
        <p class="post-date">${format(data.post.createdAt)}</p>
        <img
            src="data:image/jpeg;base64,${data.post.image}"
            class="post-img"
        />
        <article class="post-article">${data.post.content}</article>
    `;
} catch (e) {
    error = e;

    document.getElementById("content").innerHTML = await fetch("/static/html/not-found.html").then(res => res.text());
}