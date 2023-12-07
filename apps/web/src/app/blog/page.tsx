import { readAllPosts } from "@personal-website/services";
import type { Post } from "@personal-website/types";
import { Container } from "@personal-website/ui";
import { format } from "date-fns";
import Link from "next/link";
import { IoIosArrowBack } from "react-icons/io";
import Section from "@/components/Section";
import Card from "@/components/Card";

export default async function Blog() {
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
            key={v.id}
            name={v.title}
            href={`/blog/${v.id}`}
            blog
          />
        ))}
      </Section>
    </Container>
  );
}
