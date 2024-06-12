export async function createPost(formData) {
    const res = await fetch(`${process.env.REACT_APP_API_URL}/post`, {
        method: "POST",
        headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
            "Content-Type": "multipart/form-data",
        },
        body: formData,
    });

    if (!res.ok) {
        throw new Error(res.text());
    }

    return res.text();
}

export async function readPost(id) {
    const res = await fetch(`${process.env.REACT_APP_API_URL}/post/${id}`);

    if (!res.ok) {
        throw new Error(res.text());
    }

    return res.json();
}

export function readPostImage(id) {
    return `${process.env.REACT_APP_API_URL}/post/${id}/image`;
}

export async function readAllPosts() {
    const res = await fetch(`${process.env.REACT_APP_API_URL}/post`);

    if (!res.ok) {
        throw new Error(res.text());
    }

    return res.json();
}

export async function updatePost(id, formData) {
    await fetch(`${process.env.REACT_APP_API_URL}/post/${id}`, {
        method: "PUT",
        headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
            "Content-Type": "multipart/form-data",
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
