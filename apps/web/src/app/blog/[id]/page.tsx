import { readPost, readPostImageUrl } from "@personal-website/services";
import type { Post } from "@personal-website/types";
import { Container } from "@personal-website/ui";
import { format } from "date-fns";
import Image from "next/image";
import Link from "next/link";
import { notFound } from "next/navigation";
import { IoIosArrowBack } from "react-icons/io";

export default async function View({ params }: { params: { id: string } }) {
  const { id } = params;
  let data: Post;

  try {
    data = await readPost(parseInt(id));
  } catch {
    notFound();
  }

  return (
    <Container>
      <Link href="/blog" className="duration-500 hover:opacity-50">
        <IoIosArrowBack />
      </Link>
      <div className="mt-10 text-2xl">{data.title}</div>
      <div className="mt-6 text-xs text-neutral-400">
        {format(new Date(data.date), "PPP")}
      </div>
      <Image
        src={readPostImageUrl(parseInt(id))}
        alt={data.title}
        width={400}
        height={400}
        className="mt-6"
      />
      <div
        className="text-sm mt-8"
        dangerouslySetInnerHTML={{
          __html: data.content,
        }}
      />
    </Container>
  );
}
