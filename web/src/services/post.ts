import axios from "axios";
import type { Post } from "@/types/post";

export const createPost = async (text: string): Promise<number> => {
  const { data } = await axios.post(
    `${process.env.NEXT_PUBLIC_API_URL}/post`,
    { text },
    {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    }
  );

  return data;
};

export const createPostImage = async (id: number, formData: FormData) => {
  await axios.post(
    `${process.env.NEXT_PUBLIC_API_URL}/post/${id}/image`,
    formData,
    {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
        "Content-Type": "multipart/form-data",
      },
    }
  );
};

export const readPost = async (id: number): Promise<Post | null> => {
  try {
    const { data } = await axios(
      `${process.env.NEXT_PUBLIC_API_URL}/post/${id}?d=${new Date()}`
    );
    return data;
  } catch {
    return null;
  }
};

export const readPostByTitle = async (title: string): Promise<Post | null> => {
  try {
    const { data } = await axios(
      `${process.env.NEXT_PUBLIC_API_URL}/post/title/${title}?d=${new Date()}`
    );
    return data;
  } catch {
    return null;
  }
};

export const readPostImageUrl = (id: number): string => {
  return `${process.env.NEXT_PUBLIC_API_URL}/post/${id}/image`;
};

export const readAllPosts = async (): Promise<Post[]> => {
  const { data } = await axios(
    `${process.env.NEXT_PUBLIC_API_URL}/post?d=${new Date()}`
  );
  return data;
};

export const updatePost = async (id: number, text: string): Promise<void> => {
  await axios.put(
    `${process.env.NEXT_PUBLIC_API_URL}/post/${id}`,
    { text },
    {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    }
  );
};

export const deletePost = async (id: number): Promise<void> => {
  await axios.delete(`${process.env.NEXT_PUBLIC_API_URL}/post/${id}`, {
    headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
  });
};
