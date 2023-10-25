"use client";

import PostCard from "@/archive/components/PostCard";
import ArrowLink from "@/archive/components/links/ArrowLink";
import Container from "@/components/Container";
import Post from "@/types/dto/Post";
import { FC } from "react";

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
