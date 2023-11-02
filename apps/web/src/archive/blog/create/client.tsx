"use client";

import Container from "@/components/Container";
import { Spinner } from "@chakra-ui/react";
import { useRouter } from "next/router";
import { FC, useCallback, useEffect, useState } from "react";
import { FieldValues, SubmitHandler, useForm } from "react-hook-form";
import ArrowLink from "../../components/ArrowLink";
import Error from "../../components/Error";
import { createPost } from "../../services/http/post";

interface Props {
  authorized: boolean;
}

const Client: FC<Props> = ({ authorized }) => {
  const router = useRouter();
  const [error, setError] = useState(false);
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

      const res = await createPost(
        data as { title: string; description: string; body: string },
      );

      if (res.status === 200) {
        setError(false);
        const id = await res.text();
        await router.push("/blog/" + id);
      } else {
        setError(true);
        setSubmitting(false);
      }
    },
    [setSubmitting, router],
  );

  useEffect(() => {
    if (!authorized) {
      (async () => await router.push("/login"))();
    }
  }, [authorized, router]);

  return (
    <Container>
      <div className="mt-24" />
      <ArrowLink href="/blog" left text="Blog" />
      <div className="mt-12 text-2xl font-semibold">Title</div>
      <input
        className="mt-2 w-full border-b-2"
        {...register("title")}
        id="title"
        disabled={submitting}
      />
      <div className="mt-12 text-2xl font-semibold">Description</div>
      <input
        className="mt-2 w-full border-b-2"
        {...register("description")}
        id="description"
        disabled={submitting}
      />
      <div className="mt-12 text-2xl font-semibold">Body</div>
      <textarea
        className="mt-2 w-full border-b-2"
        {...register("body")}
        id="body"
        disabled={submitting}
      />
      {error && <Error text="Something went wrong." />}
      <button
        disabled={submitting}
        onClick={(e) => handleSubmit(handleUpload)(e)}
        className="mt-8 w-full bg-pink-300 py-3 text-center text-white duration-500 hover:opacity-50"
      >
        {submitting ? <Spinner size="xs" /> : "Upload Post"}
      </button>
    </Container>
  );
};

export default Client;
