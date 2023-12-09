import { readAllPosts } from "@personal-website/services";
import type { Post } from "@personal-website/types";
import BlogClient from "./client";

export default async function Blog() {
  const data: Post[] = await readAllPosts();

  return <BlogClient data={data} />;
}
