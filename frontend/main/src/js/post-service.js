export async function getPost(id) {
    const res = await fetch(`${process.env.REACT_APP_API_URL}/post/${id}`);
    const { post: data, error } = await res.json();

    if (!res.ok) {
        throw new Error(error);
    }

    return data;
}

export async function getAllPosts() {
    const res = await fetch(`${process.env.REACT_APP_API_URL}/post`);
    const { posts: data, error } = await res.json();

    if (!res.ok) {
        throw new Error(error);
    }

    return data;
}
