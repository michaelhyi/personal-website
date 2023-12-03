import axios from "axios";
import type { Post } from "types";
import type { FieldValues } from "react-hook-form";

export const createPost = async (data: FieldValues): Promise<number> => {
  const res = await axios.post(
    `${process.env.NEXT_PUBLIC_API_URL}/post`,
    data,
    {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    },
  );

  return res.data;
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
    },
  );
};

export const readPost = async (id: string): Promise<Post | null> => {
  const res = await axios(`${process.env.NEXT_PUBLIC_API_URL}/post/${id}`);

  return res.status === 200 ? res.data : null;
};

export const readAllPosts = async (): Promise<Post[]> => {
  const res = await axios(`${process.env.NEXT_PUBLIC_API_URL}/post`);
  return res.data;
};

export const readPostImageBytes = async (
  id: string,
): Promise<ArrayBuffer | null> => {
  const res = await axios(
    `${process.env.NEXT_PUBLIC_API_URL}/post/${id}/image/bytes`,
  );
  return res.data;
};

export const readPostImageUrl = (id: string): string => {
  return `${process.env.NEXT_PUBLIC_API_URL}/post/${id}/image`;
};

export const updatePost = async (
  id: string,
  data: FieldValues,
): Promise<void> => {
  await axios.post(`${process.env.NEXT_PUBLIC_API_URL}/post/${id}`, data, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem("token")}`,
    },
  });
};

export const deletePost = async (id: number): Promise<void> => {
  await axios.delete(`${process.env.NEXT_PUBLIC_API_URL}/post/${id}`, {
    headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
  });
};
