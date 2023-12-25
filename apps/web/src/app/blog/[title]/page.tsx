import { readPostByTitle, readPostImageUrl } from "@personal-website/services";
import type { Post } from "@personal-website/types";
import { BackButton, Container } from "@personal-website/ui";
import { format } from "date-fns";
import Image from "next/image";
import { notFound } from "next/navigation";

export default async function View({ params }: { params: { title: string } }) {
  const { title } = params;
  const data: Post | null = await readPostByTitle(title);

  if (!data) notFound();

  return (
    <Container>
      <BackButton href="/blog" text="Blog" />
      <div className="mt-10 text-3xl font-bold">{data.title}</div>
      <div className="mt-4 text-xs text-neutral-400">
        {format(new Date(data.date), "PPP")}
      </div>
      <Image
        src={readPostImageUrl(data.id)}
        alt={data.title}
        width={450}
        height={450}
        className="mt-6"
      />
      <div
        className="text-base mt-8"
        dangerouslySetInnerHTML={{
          __html: data.content,
        }}
      />
    </Container>
  );
}
