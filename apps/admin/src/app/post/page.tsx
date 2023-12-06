import { notFound } from "next/navigation";
import { readPost, readPostImageBytes } from "services";
import type { Post } from "types";
import Client from "./client";

export default async function Post({
  searchParams,
}: {
  searchParams: Record<string, string | undefined>;
}) {
  let data = null;

  if (searchParams.dialog === "create") {
    data = {
      title: "",
      image: new FormData(),
      body: "",
    };
  } else if (searchParams.dialog === "edit" && searchParams.id) {
    const post: Post | null = await readPost(searchParams.id);
    const image: ArrayBuffer | null = await readPostImageBytes(searchParams.id);

    if (post && image) {
      const formData = new FormData();
      formData.append("file", new File([image], `${post.title}.jpg`));

      data = {
        title: post.title,
        image: formData,
        body: post.body,
      };
    }
  }

  if (!data) {
    notFound();
  }

  return <Client data={data} />;
}
