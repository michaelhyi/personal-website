import { format } from "date-fns";
import { useEffect, useState } from "react";
import Container from "../../components/Container";
import Loading from "../../components/Loading";
import ArrowLink from "../../components/links/ArrowLink";
import { readPost, readUserByToken } from "../../services/api";
import params from "next/router";
import { useRouter } from "next/navigation";
import { BsFillPencilFill } from "react-icons/bs";

const View = () => {
  const { id } = params.query;
  const router = useRouter();
  const [data, setData] = useState(null);
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!id) return;

    readPost(id as string)
      .then(async (data) => {
        setData(data);

        const token = await localStorage.getItem("token");
        if (token) await readUserByToken(token).then((res) => setUser(res));
      })
      .finally(() => setLoading(false));
  }, [id]);

  if (loading) return <Loading />;

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
      <div className="mt-12 font-semibold text-2xl">{data.title}</div>
      <div className="text-xs mt-6 opacity-75">
        {format(new Date(data.date), "PPP")}
      </div>
      <div
        className="mt-8"
        dangerouslySetInnerHTML={{
          __html: data.body,
        }}
      />
    </Container>
  );
};

export default View;
