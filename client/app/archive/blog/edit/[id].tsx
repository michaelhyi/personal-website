import { Spinner } from "@chakra-ui/react";
import { useRouter } from "next/navigation";
import params from "next/router";
import { useCallback, useEffect, useState } from "react";
import { FieldValues, SubmitHandler, useForm } from "react-hook-form";
import NotFound from "../../../[not-found]/page";
import Container from "../../../components/Container";
import Error from "../../../components/Error";
import Loading from "../../../components/Loading";
import ArrowLink from "../../../components/links/ArrowLink";
import { readUserByToken } from "../../../services/auth";
import { deletePost, readPost, updatePost } from "../../../services/post";
import User from "../../../types/dto/User";

const Edit = () => {
  const { id } = params.query;
  const router = useRouter();
  const [found, setFound] = useState(true);
  const [error, setError] = useState(false);
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);
  const { register, handleSubmit, setValue } = useForm<FieldValues>({
    defaultValues: {
      title: "",
      description: "",
      body: "",
    },
  });

  const handleUpdate: SubmitHandler<FieldValues> = useCallback(
    async (data) => {
      setSubmitting(true);
      await updatePost(
        id as string,
        data as { title: string; description: string; body: string }
      ).then((res) => {
        if (res.status === 200) {
          setError(false);
          router.push("/blog/" + id);
        } else {
          setError(true);
          setSubmitting(false);
        }
      });
    },
    [setSubmitting, router, id]
  );

  const handleDelete = useCallback(async () => {
    setSubmitting(true);
    await deletePost(id as string).then((res) => {
      if (res.status === 200) {
        setError(false);
        router.push("/blog");
      } else {
        setError(true);
        setSubmitting(false);
      }
    });
  }, [id, router]);

  useEffect(() => {
    readUserByToken(localStorage.getItem("token") as string).then(
      async (res) => {
        if (res.status === 200) {
          const body: User = await res.json();
          if (!body.authorities[0].authority.includes("ROLE_ADMIN")) {
            router.push("/login");
          }
        } else {
          router.push("/login");
        }

        if (!id) return;

        await readPost(id as string).then(async (res) => {
          if (res.status === 200) {
            const body = await res.json();
            setValue("title", body.title);
            setValue("description", body.description);
            setValue("body", body.body);

            setLoading(false);
          } else {
            setLoading(false);
            setFound(false);
          }
        });
      }
    );
  }, [router, setLoading, id, setValue]);

  if (loading) return <Loading />;
  if (!found) return <NotFound />;

  return (
    <Container>
      <div className="mt-24" />
      <ArrowLink href={"/blog/" + id} left text="Back" />
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
        onClick={(e) => handleSubmit(handleUpdate)(e)}
        className="text-center w-full py-3 bg-pink-300 text-white mt-8 duration-500 hover:opacity-50"
      >
        {submitting ? <Spinner size="xs" /> : "Update Post"}
      </button>
      <button
        disabled={submitting}
        onClick={(e) => handleSubmit(handleDelete)(e)}
        className="text-center w-full py-3 bg-red-300 text-white mt-8 duration-500 hover:opacity-50"
      >
        {submitting ? <Spinner size="xs" /> : "Delete Post"}
      </button>
    </Container>
  );
};

export default Edit;
