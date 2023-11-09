"use client";

import { format } from "date-fns";
import { notFound } from "next/navigation";
import type { FC } from "react";
import Container from "../../../components/Container";
import type Post from "../../../types/dto/post";

interface Props {
  data: Post | null;
}

const Client: FC<Props> = ({ data }) => {
  if (data) {
    return (
      <Container>
        <div className="mt-10 font-normal">{data.title}</div>
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
  }

  return notFound();
};

export default Client;
