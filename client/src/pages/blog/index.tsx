import { useEffect, useState } from "react";
import Container from "../../components/Container";
import Loading from "../../components/Loading";
import PostCard from "../../components/PostCard";
import ArrowLink from "../../components/links/ArrowLink";
import { readAllPosts } from "../../services/api";

const Blog = () => {
  const [data, setData] = useState<any>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    readAllPosts()
      .then((data) => setData(data))
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <Loading />;

  return (
    <Container>
      <div className="mt-24" />
      <ArrowLink href="/" left text="Home" />
      <div className="flex flex-col gap-10 mt-12">
        {data.map((v: any) => (
          <PostCard key={v.id} id={v.id} title={v.title} date={v.date} />
        ))}
      </div>
    </Container>
  );
};

export default Blog;
