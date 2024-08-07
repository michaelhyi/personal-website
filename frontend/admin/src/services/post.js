export async function createPost(formData) {
    const res = await fetch(`${process.env.REACT_APP_API_URL}/post`, {
        method: "POST",
        headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
        body: formData,
    });

    if (!res.ok) {
        throw new Error(await res.text());
    }

    return res.text();
}

export async function getPost(id) {
    const res = await fetch(`${process.env.REACT_APP_API_URL}/post/${id}`);

    if (!res.ok) {
        throw new Error(await res.text());
    }

    return res.json();
}

export function getPostImage(id) {
    return `${process.env.REACT_APP_API_URL}/post/image/${id}`;
}

export async function getAllPosts() {
    const res = await fetch(`${process.env.REACT_APP_API_URL}/post`);

    if (!res.ok) {
        throw new Error(await res.text());
    }

    return res.json();
}

export async function updatePost(id, formData) {
    await fetch(`${process.env.REACT_APP_API_URL}/post/${id}`, {
        method: "PUT",
        headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
        body: formData,
    });
}

export async function deletePost(id) {
    await fetch(`${process.env.REACT_APP_API_URL}/post/${id}`, {
        method: "DELETE",
        headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
    });
}
