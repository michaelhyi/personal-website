import axios from "axios";
import type { Post } from "@/types/post";

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
