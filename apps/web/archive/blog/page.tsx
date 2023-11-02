import { readAllPosts } from "../services/http/post";
import Client from "./client";

const Blog = async () => {
  const data = await readAllPosts();

  return <Client data={data} />;
};

export default Blog;
