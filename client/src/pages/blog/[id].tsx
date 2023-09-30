import { format } from "date-fns";
import { useRouter } from "next/navigation";
import params from "next/router";
import { useEffect, useState } from "react";
import { BsFillPencilFill } from "react-icons/bs";
import Container from "../../components/Container";
import Loading from "../../components/Loading";
import ArrowLink from "../../components/links/ArrowLink";
import { readPost, readUserByToken } from "../../services/api";
import Post from "../../types/dto/Post";
import NotFound from "../404";

const View = () => {
  const { id } = params.query;
  const router = useRouter();
  const [data, setData] = useState<null | Post>(null);
  const [user, setUser] = useState<any | null>(null);
  const [found, setFound] = useState(true);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!id) return;

    readPost(id as string).then(async (res) => {
      if (res.status === 200) {
        const body = await res.json();
        setData(body);

        const token = await localStorage.getItem("token");
        if (token)
          await readUserByToken(token).then(async (res) => {
            if (res.status === 200) {
              const body = await res.json();
              setUser(body);
            }
          });
        setLoading(false);
      } else {
        setLoading(false);
        setFound(false);
      }
    });
  }, [id]);

  if (loading) return <Loading />;
  if (!found) return <NotFound />;

  return (
    <Container>
      <div className="mt-24" />
      <div className="flex justify-between">
        <ArrowLink href="/blog" left text="Blog" />
        {user && (
          <button
            className="duration-500 hover:opacity-50"
            onClick={() => router.push("/blog/edit/" + id)}
          >
            <BsFillPencilFill color="#f9a8d4" />
          </button>
        )}
      </div>
      <div className="mt-12 font-semibold text-2xl">{data!.title}</div>
      <div className="text-xs mt-6 opacity-75">
        {format(new Date(data!.date), "PPP")}
      </div>
      <div
        className="mt-8"
        dangerouslySetInnerHTML={{
          __html: data!.body,
        }}
      />
    </Container>
  );
};

export default View;
