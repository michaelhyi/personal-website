import type Post from "@/types/dto/post";

export const readPost = async (id: string): Promise<Post | null> => {
  const res = await fetch(`${process.env.API_URL}/post/${id}`);

  return res.status === 200 ? ((await res.json()) as Post) : null;
};

export const readAllPosts = async (): Promise<Post[]> => {
  const res = await fetch(`${process.env.API_URL}/post`);

  return (await res.json()) as Post[];
};
