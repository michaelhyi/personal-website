"use client";

import Container from "../../components/Container";
import PostCard from "../../components/PostCard";
import ArrowLink from "../../components/links/ArrowLink";
import Post from "../../types/dto/Post";

interface Props {
  data: Post[];
}

const Client: React.FC<Props> = ({ data }) => {
  return (
    <Container>
      <div className="mt-24" />
      <ArrowLink href="/" left text="Home" />
      <div className="flex flex-col gap-10 mt-12">
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
