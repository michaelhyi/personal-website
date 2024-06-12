export async function readPost(id) {
    const res = await fetch(`${process.env.REACT_APP_API_URL}/post/${id}`);

    if (!res.ok) {
        throw new Error();
    }

    return res.json();
}

export function readPostImage(id) {
    return `${process.env.REACT_APP_API_URL}/post/${id}/image`;
}

export async function readAllPosts() {
    const res = await fetch(`${process.env.REACT_APP_API_URL}/post`);

    if (!res.ok) {
        throw new Error();
    }

    return res.json();
}
