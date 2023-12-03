import { readAllPosts } from "services";
import Table from "@/components/Table";

const Blog = async () => {
  const data = await readAllPosts();

  return <Table data={data} />;
};

export default Blog;
