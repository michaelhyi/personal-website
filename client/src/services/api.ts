import { http } from "./http";

export const readAllPosts = async () => {
  const res = await http("/post", "GET");
  return res.json();
};
