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
