import { useEffect, useState } from "react";
import Container from "../../components/Container";
import Loading from "../../components/Loading";
import PostCard from "../../components/PostCard";
import ArrowLink from "../../components/links/ArrowLink";
import { readAllPosts } from "../../services/api";
import Post from "../../types/dto/Post";

const Blog = () => {
  const [error, setError] = useState(false);
  const [loading, setLoading] = useState(true);
  const [data, setData] = useState<Post[] | null>(null);

  useEffect(() => {
    readAllPosts()
      .then(async (res) => {
        if (res.status === 200) {
          const body: Post[] = await res.json();
          setData(body);
          setError(false);
        } else {
          setError(true);
        }
      })
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <Loading />;

  return (
    <Container>
      <div className="mt-24" />
      <ArrowLink href="/" left text="Home" />
      <div className="flex flex-col gap-10 mt-12">
        {error && <div>Something went wrong.</div>}
        {!error &&
          data !== null &&
          data!.map((v: Post) => (
            <PostCard
              key={v.id}
              id={v.id}
              title={v.title}
              description={v.description}
              date={v.date}
            />
          ))}
      </div>
    </Container>
  );
};

export default Blog;
