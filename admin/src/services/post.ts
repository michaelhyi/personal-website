import axios from "axios";
import { Post } from "@/types/post";

export const createPost = async (id: string, text: string): Promise<string> => {
  const { data } = await axios.post(
    `${process.env.NEXT_PUBLIC_API_URL}/post`,
    { id, text },
    {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    },
  );

  return data;
};

export const createPostImage = async (id: string, formData: FormData) => {
  await axios.post(
    `${process.env.NEXT_PUBLIC_API_URL}/post/${id}/image`,
    formData,
    {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
        "Content-Type": "multipart/form-data",
      },
    },
  );
};

export const readPost = async (id: string): Promise<Post | null> => {
  try {
    const { data } = await axios(
      `${process.env.NEXT_PUBLIC_API_URL}/post/${id}`,
    );
    return data;
  } catch {
    return null;
  }
};

export const readPostImageUrl = (id: string): string => {
  return `${process.env.NEXT_PUBLIC_API_URL}/post/${id}/image`;
};

export const readAllPosts = async (): Promise<Post[]> => {
  const { data } = await axios(`${process.env.NEXT_PUBLIC_API_URL}/post`);
  return data;
};

export const updatePost = async (id: string, text: string): Promise<void> => {
  await axios.put(
    `${process.env.NEXT_PUBLIC_API_URL}/post/${id}`,
    { text },
    {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    },
  );
};

export const deletePost = async (id: string): Promise<void> => {
  await axios.delete(`${process.env.NEXT_PUBLIC_API_URL}/post/${id}`, {
    headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
  });
};
