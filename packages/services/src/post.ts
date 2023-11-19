import type { Post } from "types";
import type { FieldValues } from "react-hook-form";

export const createPost = async (data: FieldValues): Promise<number> => {
  const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/post`, {
    body: JSON.stringify(data),
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("token")}`,
    },
  });

  return await res.json();
};

export const readPost = async (id: string): Promise<Post | null> => {
  const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/post/${id}`);

  return res.ok ? await res.json() : null;
};

export const readAllPosts = async (): Promise<Post[]> => {
  const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/post`);
  return await res.json();
};

export const updatePost = async (
  id: string,
  data: FieldValues,
): Promise<void> => {
  await fetch(`${process.env.NEXT_PUBLIC_API_URL}/post/${id}`, {
    body: JSON.stringify(data),
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("token")}`,
    },
  });
};

export const deletePost = async (id: number): Promise<void> => {
  await fetch(`${process.env.NEXT_PUBLIC_API_URL}/post/${id}`, {
    method: "DELETE",
    headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
  });
};
