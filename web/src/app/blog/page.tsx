export const dynamic = "force-dynamic";

import BackButton from "@/components/BackButton";
import Container from "@/components/Container";
import PostCard from "@/components/PostCard";
import { readAllPosts } from "@/services/post";
import type { Post } from "@/types/post";

export default async function Blog() {
  const data: Post[] = await readAllPosts();

  return (
    <Container absoluteFooter>
      <BackButton href="/" text="Home" />
        <div className="mt-10 flex flex-col gap-2">
          {data.map((post) => (
            <PostCard key={post.id} data={post} />
          ))}
        </div>
    </Container>
  );
}
