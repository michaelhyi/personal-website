import axios from "axios";
import type Post from "@/types/dto/post";

export const readPost = async (id: string): Promise<Post | null> => {
  const res = await axios(`${process.env.API_URL}/post/${id}`);

  return res.status === 200 ? (res.data as Post) : null;
};

export const readAllPosts = async (): Promise<Post[]> => {
  const res = await axios(`${process.env.API_URL}/post`);

  return res.data as Post[];
};
