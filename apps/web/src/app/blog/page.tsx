import { format } from "date-fns";
import Link from "next/link";
import { IoIosArrowBack } from "react-icons/io";
import Container from "@/components/Container";
import Section from "@/components/Section";
import Card from "@/components/Card";
import type Post from "@/types/dto/post";
import { readAllPosts } from "@/services/post";

const Blog = async () => {
  const data: Post[] = await readAllPosts();

  return (
    <Container absoluteFooter>
      <Link href="/" className="duration-500 hover:opacity-50">
        <IoIosArrowBack />
      </Link>
      <Section title="Blog">
        {data.map((v) => (
          <Card
            date={format(new Date(v.date), "P")}
            desc={v.description}
            key={v.id}
            name={v.title}
            href={`/blog/${v.id}`}
            blog
          />
        ))}
      </Section>
    </Container>
  );
};

export default Blog;
