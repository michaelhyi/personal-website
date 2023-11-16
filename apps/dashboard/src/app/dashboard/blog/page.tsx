import { readAllPosts } from "@/services/post";
import BlogClient from "./client";

const Blog = async () => {
  const data = await readAllPosts();

  return <BlogClient data={data} />;
};

export default Blog;
