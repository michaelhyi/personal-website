import axios from "axios";

export async function readPost(id) {
    const { data } = await axios(
        `${process.env.REACT_APP_API_URL}/post/${id}`,
    );
    return data;
}

export function readPostImage(id) {
    return `${process.env.REACT_APP_API_URL}/post/${id}/image`;
}

export async function readAllPosts() {
    const { data } = await axios(`${process.env.REACT_APP_API_URL}/post`);
    return data;
}
