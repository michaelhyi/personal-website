import { readAllPosts, readPost } from "@/services/post";
import { readUserByEmail } from "@/services/user";
import type { Post } from "@/types/post";
import type { User } from "@/types/user";
import { notFound, redirect } from "next/navigation";
import BlogClient from "./blog-client";
import EditBlogClient from "./edit-blog-client";

export default async function Blog({
  searchParams,
}: Readonly<{
  searchParams: Record<string, string | undefined>;
}>) {
  const user: User | null = await readUserByEmail();

  if (!user) redirect("/");

  let data: Post[] | null = null;
  let title: string | null = null;
  let content: string | null = null;

  if (Object.keys(searchParams).length === 0) {
    data = await readAllPosts();
  } else if (searchParams.mode === "edit" && searchParams.id) {
    const post = await readPost(searchParams.id);
    if (!post) notFound();

    title = post.title;
    content = `<h1>${post.title}</h1>${post.content}`;
  } else if (searchParams.mode !== "create") {
    notFound();
  }

  return Object.keys(searchParams).length === 0 ? (
    <BlogClient user={user} data={data!} />
  ) : (
    <EditBlogClient
      user={user}
      id={searchParams.id ? searchParams.id : null}
      title={title}
      content={JSON.parse(JSON.stringify(content))}
    />
  );
}
