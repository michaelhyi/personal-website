import { notFound } from "next/navigation";
import { readPost, readPostImageBytes } from "services";
import type { Post } from "types";
import Client from "./client";

export default async function Post({
  searchParams,
}: {
  searchParams: Record<string, string | undefined>;
}) {
  let content: string | null = "";
  let image = null;
  let redirect = false;

  if (searchParams.dialog === "edit" && searchParams.id) {
    let post: Post;
    let imageBuffer: ArrayBuffer;

    try {
      post = await readPost(searchParams.id);
      imageBuffer = await readPostImageBytes(searchParams.id);

      content = `<h1>${post.title}</h1>${post.body}`;
      image = new File([imageBuffer], `${post.title}.jpg`);
    } catch {
      redirect = true;
    }
  }

  if (redirect) {
    notFound();
  }

  return <Client content={content} image={image} />;
}
