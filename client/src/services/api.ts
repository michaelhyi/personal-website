import { http } from "./http";
import qs from "query-string";

export const readPost = async (id: string) => {
  const res = await http("/post/" + id, "GET");
  return res.json();
};

export const readAllPosts = async () => {
  const res = await http("/post?" + qs.stringify({ date: new Date() }), "GET");
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

export const createPost = async (data: { title: string; body: string }) => {
  const res = await http("/post", "POST", data);
  return res.text();
};

export const updatePost = async (id: string, title: string, body: string) => {
  await http(
    "/post/" +
      id +
      "?" +
      qs.stringify({
        title,
        body,
      }),
    "PUT"
  );
};

export const deletePost = async (id: string) => {
  await http("/post/" + id, "DELETE");
};
