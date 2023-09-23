import { useEffect, useState } from "react";
import Container from "../components/Container";
import ArrowLink from "../components/links/ArrowLink";
import { readAllPosts } from "../services/api";
import Loading from "../components/Loading";
import PostCard from "../components/PostCard";

const Blog = () => {
  const [data, setData] = useState(null);
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
        {data.map((v) => (
          <PostCard
            key={v.id}
            id={v.id}
            title={v.title}
            body={v.body.substring(0, 200) + "..."}
            date={v.date}
          />
        ))}
      </div>
    </Container>
  );
};

export default Blog;
