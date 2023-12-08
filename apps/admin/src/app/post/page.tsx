import { readPost } from "@personal-website/services";
import type { Post } from "@personal-website/types";
import { notFound } from "next/navigation";
import Client from "./client";

export default async function Post({
  searchParams,
}: {
  searchParams: Record<string, string | undefined>;
}) {
  let title: string | null = null;
  let content: string | null = null;

  if (searchParams.dialog === "edit" && searchParams.id) {
    try {
      const post = await readPost(parseInt(searchParams.id));
      title = post.title;
      content = `<h1>${post.title}</h1>${post.content}`;
    } catch {
      notFound();
    }
  } else if (searchParams.dialog === "edit" && !searchParams.id) {
    notFound();
  }

  return (
    <Client
      id={searchParams.id ? parseInt(searchParams.id) : null}
      title={title}
      content={
        // eslint-disable-next-line @typescript-eslint/no-unsafe-assignment -- JSON.parse is safe
        JSON.parse(JSON.stringify(content))
      }
    />
  );
}
