import qs from "query-string";
import { http } from "./http";

export const readPost = async (id: string) => {
  return await http("/post/" + id, "GET");
};

export const readAllPosts = async () => {
  return await http("/post?" + qs.stringify({ date: new Date() }), "GET");
};

export const login = async (data: { email: string; password: string }) => {
  return await http("/auth/login", "POST", data);
};

export const readUserByToken = async (token: string | null) => {
  return await http("/user/" + token, "GET");
};

export const createPost = async (data: { title: string; body: string }) => {
  return await http("/post", "POST", data);
};

export const updatePost = async (
  id: string,
  data: { title: string; body: string }
) => {
  return await http("/post/" + id, "POST", data);
};

export const deletePost = async (id: string) => {
  return await http("/post/" + id, "DELETE");
};
