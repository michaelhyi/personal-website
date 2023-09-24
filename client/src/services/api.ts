import { http } from "./http";

export const readPost = async (id: string) => {
  const res = await http("/post/" + id, "GET");
  return res.json();
};

export const readAllPosts = async () => {
  const res = await http("/post", "GET");
  return res.json();
};

export const login = async (data: { email: string; password: string }) => {
  const res = await http("/auth/login", "POST", data);
  return res.json();
};

export const readUserByToken = async (token: string) => {
  const res = await http("/user/" + token, "GET");
  return res.json();
};
