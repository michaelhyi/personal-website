"use client";

import { Spinner } from "@chakra-ui/react";
import { useRouter } from "next/router";
import { useCallback, useEffect, useState } from "react";
import { FieldValues, SubmitHandler, useForm } from "react-hook-form";
import Container from "../../../components/Container";
import Error from "../../../components/Error";
import ArrowLink from "../../../components/links/ArrowLink";
import { createPost } from "../../../services/post";

interface Props {
  authorized: boolean;
}

const Client: React.FC<Props> = ({ authorized }) => {
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
        data as { title: string; description: string; body: string }
      );

      if (res.status === 200) {
        setError(false);
        const id = await res.text();
        router.push("/blog/" + id);
      } else {
        setError(true);
        setSubmitting(false);
      }
    },
    [setSubmitting, router]
  );

  useEffect(() => {
    if (!authorized) router.push("/login");
  }, [authorized, router]);

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

export default Client;
