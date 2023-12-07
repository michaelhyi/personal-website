import type { FC } from "react";
import { notFound } from "next/navigation";
import { format } from "date-fns";
import Link from "next/link";
import Image from "next/image";
import { IoIosArrowBack } from "react-icons/io";
import { readPost, readPostImageUrl } from "services";
import type { Post } from "types";
import { Container } from "ui";

interface IParams {
  params: { id: string };
}

const View: FC<IParams> = async ({ params }) => {
  const { id } = params;
  let data: Post;

  try {
    data = await readPost(id);
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
        src={readPostImageUrl(id)}
        alt={data.title}
        width={400}
        height={400}
        className="mt-6"
      />
      <div
        className="text-sm mt-8"
        dangerouslySetInnerHTML={{
          __html: data.body,
        }}
      />
    </Container>
  );
};

export default View;
