export async function getPost(id) {
    const res = await fetch(`http://localhost:8080/post/${id}`);
    const { post: data, error } = await res.json();

    if (!res.ok) {
        throw new Error(error);
    }

    return data;
}

export async function getAllPosts() {
    const res = await fetch("http://localhost:8080/v2/post");
    const { posts: data, error } = await res.json();

    if (!res.ok) {
        throw new Error(error);
    }

    return data;
}
