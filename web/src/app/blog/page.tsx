export const dynamic = "force-dynamic";

import BackButton from "@/components/BackButton";
import Container from "@/components/Container";
import Hoverable from "@/components/Hoverable";
import { readAllPosts } from "@/services/post";
import type { Post } from "@/types/post";
import Link from "next/link";
import { FiArrowUpRight } from "react-icons/fi";

export default async function Blog() {
  const data: Post[] = await readAllPosts();

  return (
    <Container absoluteFooter>
      <BackButton href="/" text="Home" />
      <div className="mt-10 flex flex-col gap-2">
        {data.map((post: Post) => (
          <Hoverable key={post.id}>
            <Link
              className="flex text-sm font-medium"
              href={`${process.env.NEXT_PUBLIC_WEB_URL}/blog/${post.id}`}
            >
              {post.title} <FiArrowUpRight />
            </Link>
          </Hoverable>
        ))}
      </div>
    </Container>
  );
}
