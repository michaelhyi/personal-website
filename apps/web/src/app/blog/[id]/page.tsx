import type { FC } from "react";
// import { notFound } from "next/navigation";
import { format } from "date-fns";
import Image from "next/image";
import type Post from "@/types/dto/post";
import Container from "@/components/Container";
import { readPost } from "@/services/post";

interface IParams {
  params: { id: string };
}

const View: FC<IParams> = ({ params }) => {
  const { id } = params;
  const data: Post | null = readPost(id);

  // if (!data) notFound();

  return (
    <Container>
      <div className="mt-10 text-2xl">{data.title}</div>
      <div className="mt-6 text-xs text-neutral-400">
        {format(new Date(data.date), "PPP")}
      </div>
      <Image
        src={data.image}
        alt={data.title}
        width={400}
        height={400}
        className="mt-6"
      />
      <div className="bg-pink-300 p-4 mt-8">Rating: {data.rating} / 10</div>
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
