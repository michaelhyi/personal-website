"use client";

import type { Post } from "@personal-website/types";
import { Container, PostCard } from "@personal-website/ui";
import Link from "next/link";
import { IoIosArrowBack } from "react-icons/io";
import Section from "@/components/Section";

export default function BlogClient({ data }: { data: Post[] }) {
  return (
    <Container absoluteFooter>
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
