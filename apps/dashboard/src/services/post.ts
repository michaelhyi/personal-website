import axios from "axios";
import type { FieldValues } from "react-hook-form";
import type Post from "types/post";

export const createPost = async (data: FieldValues) => {
  await axios.post(`${process.env.NEXT_PUBLIC_API_URL}/post`, data, {
    headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
  });
};

export const readPost = async (id: string) => {
  const res = await axios(`${process.env.NEXT_PUBLIC_API_URL}/post/${id}`);

  return res.status === 200 ? (res.data as Post) : null;
};

export const readAllPosts = async () => {
  const res = await axios(`${process.env.NEXT_PUBLIC_API_URL}/post`);
  return res.data as Post[];
};

export const updatePost = async (id: string, data: FieldValues) => {
  await axios.post(`${process.env.NEXT_PUBLIC_API_URL}/post/${id}`, data, {
    headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
  });
};

export const deletePost = async (id: number) => {
  await axios.delete(`${process.env.NEXT_PUBLIC_API_URL}/post/${id}`, {
    headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
  });
};
