import axios from "axios";
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
    try {
      (async () => {
        const { data } = await axios(
          `${process.env.REACT_APP_API_URL}/post/${id}`
        );

        setData(data);
      })();
    } catch {
      setNotFound(true);
    }
  }, [id]);

  if (!data) return <Loading />;

  if (notFound) {
    return (
      <Container absoluteFooter>
        <Center>
          <div className="text-[10px] text-neutral-300">Not Found</div>
        </Center>
      </Container>
    );
  }

  return (
    <Container>
      <BackButton href="/blog" text="Blog" />
      <div className="mt-10 text-3xl font-bold">{data.title}</div>
      <div className="mt-4 text-xs text-neutral-400">
        {format(new Date(data.date), "PPP")}
      </div>
      <img
        src={`${process.env.REACT_APP_API_URL}/post/${id}/image`}
        alt={data.title}
        className="w-full mt-6"
        width={1000}
        height={1000}
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
