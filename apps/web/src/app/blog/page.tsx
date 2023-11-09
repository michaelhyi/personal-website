import { format } from "date-fns";
import Container from "@/components/Container";
import Section from "@/components/Section";
import Card from "@/components/Card";
import type Post from "@/types/dto/post";

const Blog = async () => {
  const res = await fetch("http://localhost:3000/api/v1/post");
  const data: Post[] = (await res.json()) as Post[];

  return (
    <Container>
      <Section title="Blog">
        {data.map((v) => (
          <Card
            date={format(new Date(v.date), "P")}
            desc={v.description}
            key={v.id}
            name={v.title}
          />
        ))}
      </Section>
    </Container>
  );
};

export default Blog;
