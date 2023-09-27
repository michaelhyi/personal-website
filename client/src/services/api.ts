import { http } from "./http";
import qs from "query-string";

export const readPost = async (id: string) => {
  const res = await http("/post/" + id, "GET");
  return res;
};

export const readAllPosts = async () => {
  const res = await http("/post?" + qs.stringify({ date: new Date() }), "GET");
  return await res.json();
};

export const login = async (data: { email: string; password: string }) => {
  const res = await http("/auth/login", "POST", data);
  return res;
};

export const readUserByToken = async (token: string | null) => {
  const res = await http("/user/" + token, "GET");
  return res;
};

export const createPost = async (data: { title: string; body: string }) => {
  const res = await http("/post", "POST", data);
  return await res.text();
};

export const updatePost = async (
  id: string,
  data: { title: string; body: string }
) => {
  await http("/post/" + id, "POST", data);
};

export const deletePost = async (id: string) => {
  await http("/post/" + id, "DELETE");
};
