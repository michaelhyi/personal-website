import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import Container from "../../components/Container";
import Loading from "../../components/Loading";
import ArrowLink from "../../components/links/ArrowLink";
import { readUserByToken } from "../../services/api";

const Create = () => {
  const router = useRouter();
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    readUserByToken(localStorage.getItem("token") as string)
      .then((res) => {
        if (!res.roles.includes("ROLE_ADMIN")) {
          router.push("/login");
        }
      })
      .finally(() => setLoading(false));
  }, [router, setLoading]);

  if (loading) return <Loading />;

  return (
    <Container>
      <div className="mt-24" />
      <ArrowLink href="/blog" left text="Blog" />
      <div className="mt-12 font-semibold text-2xl">Title</div>
      <input
        className="border-b-2 w-full mt-2"
        // {...register("email")}
        id="email"
        // disabled={submitting}
      />
      <div className="mt-12 font-semibold text-2xl">Body</div>
      <textarea
        className="border-b-2 w-full mt-2"
        // {...register("email")}
        id="email"
        // disabled={submitting}
      />
    </Container>
  );
};

export default Create;
