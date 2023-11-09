"use client";

import type { FC } from "react";
import Container from "@/components/Container";
import Section from "@/components/Section";
import type Post from "@/types/dto/post";

interface Props {
  data: Post[];
}

const Client: FC<Props> = ({ data }) => {
  return (
    <Container>
      <Section title="Blog">
        {data.map((v) => (
          <div key={v.id}>{v.title}</div>
        ))}
      </Section>
    </Container>
  );
};

export default Client;
