import { readAllPosts } from "services";
import PostTable from "@/components/PostTable";

const Blog = async () => {
  const data = await readAllPosts();

  return <PostTable title="Blog" data={data} />;
};

export default Blog;
