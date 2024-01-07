import { notFound, redirect } from "next/navigation";
import { readAllPosts, readPost } from "@/services/post";
import { readUserByEmail } from "@/services/user";
import type { Post } from "@/types/post";
import type { User } from "@/types/user";
import BlogClient from "./blog-client";
import EditBlogClient from "./edit-blog-client";

export default async function Blog({
  searchParams,
}: {
  searchParams: Record<string, string | undefined>;
}) {
  const user: User | null = await readUserByEmail();
  let title: string | null = null;
  let content: string | null = null;

  if (!user) redirect("/");

  if (Object.keys(searchParams).length === 0) {
    const data: Post[] = await readAllPosts();

    return <BlogClient user={user} data={data} />;
  } else if (searchParams.mode === "edit" && searchParams.id) {
    const post = await readPost(parseInt(searchParams.id));

    if (!post) notFound();

    title = post.title;
    content = `<h1>${post.title}</h1>${post.content}`;
  } else if (searchParams.mode !== "create") {
    notFound();
  }

  return (
    <EditBlogClient
      user={user}
      id={searchParams.id ? parseInt(searchParams.id) : null}
      title={title}
      content={JSON.parse(JSON.stringify(content))}
    />
  );
}
