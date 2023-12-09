import { readAllPosts } from "@personal-website/services";
import type { Post } from "@personal-website/types";
import { Container, PostCard } from "@personal-website/ui";
import Link from "next/link";
import { IoIosArrowBack } from "react-icons/io";
import Section from "@/components/Section";

export default async function Blog() {
  const data: Post[] = await readAllPosts();

  return (
    <Container>
      <Link href="/" className="duration-500 hover:opacity-50">
        <IoIosArrowBack />
      </Link>
      <Section title="Blog">
        {data.map((post, index) => (
          <PostCard key={post.id} data={post} index={index} />
        ))}
      </Section>
    </Container>
  );
}
