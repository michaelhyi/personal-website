"use client";

import { Spinner } from "@chakra-ui/react";
import { notFound, useRouter } from "next/navigation";
import { useCallback, useEffect, useState } from "react";
import { FieldValues, SubmitHandler, useForm } from "react-hook-form";
import Container from "../../../../components/Container";
import Error from "../../../../components/Error";
import ArrowLink from "../../../../components/links/ArrowLink";
import { deletePost, updatePost } from "../../../../services/post";
import Post from "../../../../types/dto/Post";

interface Props {
  id: string;
  authorized: boolean;
  data: Post;
}

const Client: React.FC<Props> = ({ id, authorized, data }) => {
  const router = useRouter();
  const [error, setError] = useState(false);
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

      const res = await updatePost(
        id as string,
        data as { title: string; description: string; body: string }
      );

      if (res.status === 200) {
        setError(false);
        router.push("/blog/" + id);
      } else {
        setError(true);
        setSubmitting(false);
      }
    },
    [setSubmitting, router, id]
  );

  const handleDelete = useCallback(async () => {
    setSubmitting(true);

    const res = await deletePost(id as string);

    if (res.status === 200) {
      setError(false);
      router.push("/blog");
    } else {
      setError(true);
      setSubmitting(false);
    }
  }, [id, router]);

  useEffect(() => {
    if (!id || !data) notFound();
    else if (!authorized) router.push("/login");
    else {
      setValue("title", data.title);
      setValue("description", data.description);
      setValue("body", data.body);
    }
  }, [id, authorized, data, setValue, router]);

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

export default Client;
