import { readAllPosts } from "@personal-website/services";
import type { Post } from "@personal-website/types";
import { BackButton, Container, PostCard } from "@personal-website/ui";
import Section from "@/components/Section";

export default async function Blog() {
  const data: Post[] = await readAllPosts();

  return (
    <Container>
      <BackButton href="/" text="Home" />
      <Section title="Blog">
        {data.map((post, index) => (
          <PostCard key={post.id} data={post} index={index} />
        ))}
      </Section>
    </Container>
  );
}
