import { format } from "date-fns";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import BackButton from "../components/BackButton";
import Center from "../components/Center";
import Container from "../components/Container";
import Loading from "../components/Loading";

export default function ViewPost() {
  const { id } = useParams();
  const [data, setData] = useState(null);
  const [notFound, setNotFound] = useState(false);

  useEffect(() => {
    (async () => {
      const res = await fetch(`${process.env.REACT_APP_API_URL}/v1/post/${id}`);

      if (!res.ok) {
        setNotFound(true);
      } else {
        setData(await res.json());
      }
    })();
  }, [id]);

  if (notFound) {
    return (
      <Container absoluteFooter>
        <Center>
          <div className="text-[10px] text-neutral-300">Not Found</div>
        </Center>
      </Container>
    );
  }

  if (!data) return <Loading />;

  return (
    <Container>
      <BackButton href="/blog" text="Blog" />
      <div className="mt-10 text-3xl font-bold">{data.title}</div>
      <div className="mt-4 text-xs text-neutral-400">
        {format(new Date(data.date), "PPP")}
      </div>
      <img
        src={`${process.env.REACT_APP_API_URL}/v1/post/${id}/image`}
        alt={data.title}
        className="w-full mt-6"
      />
      <div
        className="text-[15px] mt-8"
        dangerouslySetInnerHTML={{
          __html: data.content,
        }}
      />
    </Container>
  );
}