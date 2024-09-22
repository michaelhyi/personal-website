export async function createPost(formData) {
        const res = await fetch(`${process.env.REACT_APP_API_URL}/post`, {
                method: "POST",
                headers: {
                        Authorization: `Bearer ${localStorage.getItem("token")}`,
                },
                body: formData,
        });
        const { postId, error } = await res.json();

        if (!res.ok) {
                throw new Error(error);
        }

        return postId;
}

export async function getPost(id) {
        const res = await fetch(`${process.env.REACT_APP_API_URL}/post/${id}`);
        const { post, error } = await res.json();

        if (!res.ok) {
                throw new Error(error);
        }

        return post;
}

export async function getAllPosts() {
        const res = await fetch(`${process.env.REACT_APP_API_URL}/post`);
        const { posts, error } = await res.json();

        if (!res.ok) {
                throw new Error(error);
        }

        return posts;
}

export async function updatePost(id, formData) {
        await fetch(`${process.env.REACT_APP_API_URL}/post/${id}`, {
                method: "PATCH",
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
