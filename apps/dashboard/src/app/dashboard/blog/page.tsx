import { readAllPosts } from "services";
import Page from "@/components/Page";

const Blog = async () => {
  const data = await readAllPosts();

  return <Page title="Blog" data={data} />;
};

export default Blog;
