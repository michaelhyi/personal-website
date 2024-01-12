import axios from "axios";
import type { Post } from "@/types/post";

export const readPost = async (id: number): Promise<Post | null> => {
  try {
    const { data } = await axios(
      `${process.env.NEXT_PUBLIC_API_URL}/post/${id}`
    );
    return data;
  } catch {
    return null;
  }
};

export const readPostByTitle = async (title: string): Promise<Post | null> => {
  try {
    const { data } = await axios(
      `${process.env.NEXT_PUBLIC_API_URL}/post/title/${title}`
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
  const { data } = await axios(`${process.env.NEXT_PUBLIC_API_URL}/post`);
  return data;
};
