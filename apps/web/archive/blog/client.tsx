"use client";

import { FC } from "react";
import Container from "../../src/components/container";
import Post from "../../src/types/dto/post";
import ArrowLink from "../components/arrow-link";
import PostCard from "../components/post-card";

interface Props {
  data: Post[];
}

const Client: FC<Props> = ({ data }) => {
  return (
    <Container>
      <div className="mt-24" />
      <ArrowLink href="/" left text="Home" />
      <div className="mt-12 flex flex-col gap-10">
        {data !== null &&
          data!.map((v: Post) => (
            <PostCard
              key={v.id}
              id={v.id}
              title={v.title}
              description={v.description}
              date={v.date}
            />
          ))}
      </div>
    </Container>
  );
};

export default Client;
