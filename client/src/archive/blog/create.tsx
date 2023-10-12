import { Spinner } from "@chakra-ui/react";
import { useRouter } from "next/navigation";
import { useCallback, useEffect, useState } from "react";
import { FieldValues, SubmitHandler, useForm } from "react-hook-form";
import Container from "../../components/Container";
import Error from "../../components/Error";
import Loading from "../../components/Loading";
import ArrowLink from "../../components/links/ArrowLink";
import { createPost, readUserByToken } from "../../services/api";
import User from "../../types/dto/User";

const Create = () => {
  const router = useRouter();
  const [error, setError] = useState(false);
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);
  const { register, handleSubmit } = useForm<FieldValues>({
    defaultValues: {
      title: "",
      description: "",
      body: "",
    },
  });

  const handleUpload: SubmitHandler<FieldValues> = useCallback(
    async (data) => {
      setSubmitting(true);

      await createPost(
        data as { title: string; description: string; body: string }
      ).then(async (res) => {
        if (res.status === 200) {
          setError(false);

          const id = await res.text();
          router.push("/blog/" + id);
        } else {
          setError(true);
          setSubmitting(false);
        }
      });
    },
    [setSubmitting, router]
  );

  useEffect(() => {
    readUserByToken(localStorage.getItem("token") as string).then(
      async (res) => {
        if (res.status === 200) {
          const body: User = await res.json();
          if (!body.authorities[0].authority.includes("ROLE_ADMIN")) {
            router.push("/login");
          } else {
            setLoading(false);
          }
        } else {
          router.push("/login");
        }
      }
    );
  }, [router, setLoading]);

  if (loading) return <Loading />;

  return (
    <Container>
      <div className="mt-24" />
      <ArrowLink href="/blog" left text="Blog" />
      <div className="mt-12 font-semibold text-2xl">Title</div>
      <input
        className="border-b-2 w-full mt-2"
        {...register("title")}
        id="title"
        disabled={submitting}
      />
      <div className="mt-12 font-semibold text-2xl">Description</div>
      <input
        className="border-b-2 w-full mt-2"
        {...register("description")}
        id="description"
        disabled={submitting}
      />
      <div className="mt-12 font-semibold text-2xl">Body</div>
      <textarea
        className="border-b-2 w-full mt-2"
        {...register("body")}
        id="body"
        disabled={submitting}
      />
      {error && <Error text="Something went wrong." />}
      <button
        disabled={submitting}
        onClick={(e) => handleSubmit(handleUpload)(e)}
        className="text-center w-full py-3 bg-pink-300 text-white mt-8 duration-500 hover:opacity-50"
      >
        {submitting ? <Spinner size="xs" /> : "Upload Post"}
      </button>
    </Container>
  );
};

export default Create;
