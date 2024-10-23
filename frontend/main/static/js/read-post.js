import format from "./date-util.js"
import { getPost } from "./post-service.js";

document.getElementById("content").innerHTML = await fetch("/static/html/loading.html").then(res => res.text());

const id = window.location.pathname.split("/").pop();

try {
    const data = await getPost(id);

    document.getElementById("content").innerHTML = `
        <a href="/blog" class="back-arrow">&#8592;</a>
        <h1 class="post-title">${data.title}</h1>
        <p class="post-date">${format(data.createdAt)}</p>
        <img src="data:image/jpeg;base64,${data.image}" class="post-img"/>
        <article class="post-article">${data.content}</article>
    `;
} catch {
    document.getElementById("content").innerHTML = await fetch("/static/html/not-found.html").then(res => res.text());
}
