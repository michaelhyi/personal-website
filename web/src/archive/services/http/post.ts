import qs from "query-string";
import { http } from "./http";

export const createPost = async (data: {
   title: string;
   description: string;
   body: string;
}) => {
   return await http("/post", "POST", data);
};

export const readPost = async (id: string) => {
   return await http("/post/" + id, "GET");
};

export const readAllPosts = async () => {
   const res = await http("/post?" + qs.stringify({ date: new Date() }), "GET");

   return await res.json();
};

export const updatePost = async (
   id: string,
   data: { title: string; description: string; body: string }
) => {
   return await http("/post/" + id, "POST", data);
};

export const deletePost = async (id: string) => {
   return await http("/post/" + id, "DELETE");
};
