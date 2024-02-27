import axios from "axios";

function authConfig() {
    return {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
    };
}

export async function createPost(text) {
    const { data } = await axios.post(
        `${process.env.REACT_APP_API_URL}/v1/post`,
        { text },
        authConfig(),
    );

    return data;
}

export async function createPostImage(id, formData) {
    await axios.post(
        `${process.env.REACT_APP_API_URL}/v1/post/${id}/image`,
        formData,
        {
            headers: {
                ...authConfig().headers,
                "Content-Type": "multipart/form-data",
            }
        },
    );
}

export async function readPost(id) {
    const { data } = await axios(
        `${process.env.REACT_APP_API_URL}/v1/post/${id}`,
    );
    return data;
}

export function readPostImage(id) {
    return `${process.env.REACT_APP_API_URL}/v1/post/${id}/image`;
}

export async function readAllPosts() {
    const { data } = await axios(`${process.env.REACT_APP_API_URL}/v1/post`);
    return data;
}

export async function updatePost(id, text) {
    await axios.put(
        `${process.env.REACT_APP_API_URL}/v1/post/${id}`,
        { text },
        authConfig(),
    );
}

export async function deletePost(id) {
    await axios.delete(
        `${process.env.REACT_APP_API_URL}/v1/post/${id}`,
        authConfig(),
    );
}
