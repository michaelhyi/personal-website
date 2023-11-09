import type { FC } from "react";
import { notFound } from "next/navigation";
import { format } from "date-fns";
import type Post from "@/types/dto/post";
import Container from "@/components/Container";

interface IParams {
  params: { id: string };
}

const View: FC<IParams> = async ({ params }) => {
  const { id } = params;
  const res = await fetch(`http://localhost:3000/api/v1/post/${id}`);
  const data: Post | null =
    res.status === 200 ? ((await res.json()) as Post) : null;

  if (!data) notFound();

  return (
    <Container>
      <div className="mt-24" />
      <div className="mt-12 text-2xl font-semibold">{data.title}</div>
      <div className="mt-6 text-xs opacity-75">
        {format(new Date(data.date), "PPP")}
      </div>
      <div
        className="mt-8"
        dangerouslySetInnerHTML={{
          __html: data.body,
        }}
      />
    </Container>
  );
};

export default View;
