import axios from "axios";

export const createPost = async (id, text) => {
  const { data } = await axios.post(
    `${process.env.REACT_APP_API_URL}/post`,
    { id, text },
    {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    }
  );

  return data;
};

export const createPostImage = async (id, formData) => {
  await axios.post(
    `${process.env.REACT_APP_API_URL}/post/${id}/image`,
    formData,
    {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
        "Content-Type": "multipart/form-data",
      },
    }
  );
};

export const readPost = async (id) => {
  const { data } = await axios(`${process.env.REACT_APP_API_URL}/post/${id}`);
  return data;
};

export const readPostImageUrl = (id) => {
  return `${process.env.REACT_APP_API_URL}/post/${id}/image`;
};

export const readAllPosts = async () => {
  const { data } = await axios(`${process.env.REACT_APP_API_URL}/post`);
  return data;
};

export const updatePost = async (id, text) => {
  await axios.put(
    `${process.env.REACT_APP_API_URL}/post/${id}`,
    { text },
    {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    }
  );
};

export const deletePost = async (id) => {
  await axios.delete(`${process.env.REACT_APP_API_URL}/post/${id}`, {
    headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
  });
};
