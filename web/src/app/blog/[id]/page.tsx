import { readPost, readPostImageUrl } from "@/services/post";
import BackButton from "@/components/BackButton";
import Container from "@/components/Container";
import { format } from "date-fns";
import Image from "next/image";
import { notFound } from "next/navigation";
import type { Post } from "@/types/post";

export default async function View({ params }: { params: { id: string } }) {
  const { id } = params;
  const data: Post | null = await readPost(id);

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
