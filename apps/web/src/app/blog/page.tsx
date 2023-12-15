import { readAllPosts } from "@personal-website/services";
import type { Post } from "@personal-website/types";
import { BackButton, Container, PostCard } from "@personal-website/ui";

export default async function Blog() {
  const data: Post[] = await readAllPosts();

  return (
    <Container>
      <BackButton href="/" text="Home" />
      <div className="mt-8">
        <div className="font-bold text-3xl">Blog</div>
        <div className="mt-2 text-sm font-medium text-neutral-400">
          An exploration of my enthusiasm for cinema through reviews, analyses,
          and essays on various films.
        </div>
        <div className="mt-12 flex flex-col gap-8">
          {data.map((post, index) => (
            <PostCard key={post.id} data={post} index={index} />
          ))}
        </div>
      </div>
    </Container>
  );
}
