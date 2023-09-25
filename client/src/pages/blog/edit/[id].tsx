import { Spinner } from "@chakra-ui/react";
import { useRouter } from "next/navigation";
import params from "next/router";
import { useCallback, useEffect, useState } from "react";
import { FieldValues, SubmitHandler, useForm } from "react-hook-form";
import Container from "../../../components/Container";
import Loading from "../../../components/Loading";
import ArrowLink from "../../../components/links/ArrowLink";
import {
  deletePost,
  readPost,
  readUserByToken,
  updatePost,
} from "../../../services/api";

const Edit = () => {
  const { id } = params.query;
  const router = useRouter();
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);
  const { register, handleSubmit, setValue } = useForm<FieldValues>({
    defaultValues: {
      title: "",
      body: "",
    },
  });

  const handleUpdate: SubmitHandler<FieldValues> = useCallback(
    async (data) => {
      setSubmitting(true);
      await updatePost(id as string, data.title, data.body)
        .then(() => router.push("/blog/" + id))
        .finally(() => setSubmitting(false));
    },
    [setSubmitting, router, id]
  );

  const handleDelete = useCallback(async () => {
    setSubmitting(true);
    await deletePost(id as string)
      .then(() => router.push("/blog"))
      .finally(() => setSubmitting(false));
  }, [id]);

  useEffect(() => {
    readUserByToken(localStorage.getItem("token") as string)
      .then(async (res) => {
        if (!res.roles.includes("ROLE_ADMIN")) {
          router.push("/login");
        }
        if (!id) return;
        await readPost(id as string).then(async (data) => {
          setValue("title", data.title);
          setValue("body", data.body);
        });
      })
      .finally(() => setLoading(false));
  }, [router, setLoading, id, setValue]);

  if (loading) return <Loading />;

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
      <div className="mt-12 font-semibold text-2xl">Body</div>
      <textarea
        className="border-b-2 w-full mt-2"
        {...register("body")}
        id="body"
        disabled={submitting}
      />
      <button
        onClick={(e) => handleSubmit(handleUpdate)(e)}
        className="text-center w-full py-3 bg-pink-300 text-white mt-8 duration-500 hover:opacity-50"
      >
        {submitting ? <Spinner size="xs" /> : "Update Post"}
      </button>
      <button
        onClick={(e) => handleSubmit(handleDelete)(e)}
        className="text-center w-full py-3 bg-red-300 text-white mt-8 duration-500 hover:opacity-50"
      >
        {submitting ? <Spinner size="xs" /> : "Delete Post"}
      </button>
    </Container>
  );
};

export default Edit;
