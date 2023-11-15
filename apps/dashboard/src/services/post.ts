import axios from "axios";
import type Post from "@/types/post";

export const readAllPosts = async () => {
  const res = await axios(`${process.env.NEXT_PUBLIC_API_URL}/post`);
  return res.data as Post[];
};
