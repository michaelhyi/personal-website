import axios from "axios";

export const readPost = async (id) => {
  const { data } = await axios(
    `${process.env.REACT_APP_API_URL}/v1/post/${id}`
  );
  return data;
};

export const readPostImage = (id) => {
  return `${process.env.REACT_APP_API_URL}/v1/post/${id}/image`;
};

export const readAllPosts = async () => {
  const { data } = await axios(`${process.env.REACT_APP_API_URL}/v1/post`);
  return data;
};
