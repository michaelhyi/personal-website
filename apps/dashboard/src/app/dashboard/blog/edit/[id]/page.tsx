import { notFound } from "next/navigation";
import type { Post } from "types";
import { readPost } from "services";
import EditPostClient from "./client";

const EditPost = async ({ params }: { params: { id: string } }) => {
  const data: Post | null = await readPost(params.id);

  if (!data) {
    notFound();
  }

  return <EditPostClient data={data} />;
};

export default EditPost;
