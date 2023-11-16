import { readAllPosts } from "services";
import BlogClient from "./client";

const Blog = async () => {
  const data = await readAllPosts();

  return <BlogClient data={data} />;
};

export default Blog;
