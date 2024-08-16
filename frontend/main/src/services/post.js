export async function getPost(id) {
    const res = await fetch(`${process.env.REACT_APP_API_URL}/post/${id}`);
    const { post, error } = await res.json();

    if (!res.ok) {
        throw new Error(error);
    }

    return post;
}

export async function getPostImage(id) {
    const res = await fetch(
        `${process.env.REACT_APP_API_URL}/post/image/${id}`,
    );
    const { image, error } = await res.json();

    if (!res.ok) {
        throw new Error(error);
    }

    return image;
}

export async function getAllPosts() {
    const res = await fetch(`${process.env.REACT_APP_API_URL}/post`);
    const { posts, error } = await res.json();

    if (!res.ok) {
        throw new Error(error);
    }

    return posts;
}
