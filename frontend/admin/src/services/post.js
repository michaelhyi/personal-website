import axios from "axios";

import authConfig from "./authConfig";

export async function createPost(formData) {
    const { data } = await axios.post(
        `${process.env.REACT_APP_API_URL}/post`,
        formData,
        {
            headers: {
                ...authConfig().headers,
                "Content-Type": "multipart/form-data",
            },
        },
    );

    return data;
}

export async function readPost(id) {
    const { data } = await axios(`${process.env.REACT_APP_API_URL}/post/${id}`);
    return data;
}

export function readPostImage(id) {
    return `${process.env.REACT_APP_API_URL}/post/${id}/image`;
}

export async function readAllPosts() {
    const { data } = await axios(`${process.env.REACT_APP_API_URL}/post`);
    return data;
}

export async function updatePost(id, formData) {
    await axios.put(`${process.env.REACT_APP_API_URL}/post/${id}`, formData, {
        headers: {
            ...authConfig().headers,
            "Content-Type": "multipart/form-data",
        },
    });
}

export async function deletePost(id) {
    await axios.delete(
        `${process.env.REACT_APP_API_URL}/post/${id}`,
        authConfig(),
    );
}
