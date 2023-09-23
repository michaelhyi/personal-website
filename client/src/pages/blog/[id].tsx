import { format } from "date-fns";
import { useSearchParams } from "next/navigation";
import { useEffect, useState } from "react";
import Container from "../../components/Container";
import Loading from "../../components/Loading";
import ArrowLink from "../../components/links/ArrowLink";
import { readPost } from "../../services/api";

const View = () => {
  const params = useSearchParams();
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    readPost(params.get("id") as string)
      .then((data) => setData(data))
      .finally(() => setLoading(false));
  }, [params]);

  if (loading) return <Loading />;

  return (
    <Container>
      <div className="mt-24" />
      <ArrowLink href="/blog" left text="Blog" />
      <div className="mt-12 font-semibold text-2xl">{data.title}</div>
      <div className="text-xs mt-6 opacity-75">
        {format(new Date(data.date), "PPP")}
      </div>
      <div
        className="text-sm mt-8"
        dangerouslySetInnerHTML={{ __html: data.body }}
      />
    </Container>
  );
};

export default View;
