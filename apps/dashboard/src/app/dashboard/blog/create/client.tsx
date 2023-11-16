"use client";

import type { AxiosError } from "axios";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useState, useCallback } from "react";
import { useForm } from "react-hook-form";
import type { FieldValues, SubmitHandler } from "react-hook-form";
import { FaArrowLeft } from "react-icons/fa";
import { createPost } from "services";
import Container from "@/components/Container";

const CreatePostClient = () => {
  const router = useRouter();
  // const [error, setError] = useState<string | null>(null);
  const [submitting, setSubmitting] = useState<boolean>(false);
  const { register, handleSubmit } = useForm({
    defaultValues: {
      title: "",
      image: "",
      description: "",
      body: "",
    },
  });

  const handleCreatePost: SubmitHandler<FieldValues> = useCallback(
    async (data) => {
      setSubmitting(true);

      try {
        await createPost(data);
        router.push("/dashboard/blog");
      } catch (e) {
        const { response } = e as AxiosError;

        if (response) {
          // setError(response.data as string);
        }
      } finally {
        setSubmitting(false);
      }
    },
    [router],
  );

  return (
    <Container>
      <Link
        href="/dashboard/blog"
        className="text-neutral-300 duration-500 hover:opacity-50"
      >
        <FaArrowLeft />
      </Link>
      <div className="font-bold text-3xl mt-8">Create Post</div>
      <form
        className="mt-8 flex flex-col gap-2"
        onSubmit={handleSubmit(handleCreatePost)}
      >
        <label htmlFor="title" className="text-sm">
          Title
        </label>
        <input
          className="bg-neutral-800 border-[1px] border-neutral-300 rounded-md w-96 h-10 px-2"
          id="title"
          disabled={submitting}
          {...register("title")}
        />
        <label htmlFor="image" className="text-sm mt-4">
          Image
        </label>
        <input
          className="bg-neutral-800 border-[1px] border-neutral-300 rounded-md w-96 h-10 px-2"
          id="image"
          disabled={submitting}
          {...register("image")}
        />
        <label htmlFor="description" className="text-sm mt-4">
          Description
        </label>
        <input
          className="bg-neutral-800 border-[1px] border-neutral-300 rounded-md w-96 h-10 px-2"
          id="description"
          disabled={submitting}
          {...register("description")}
        />
        <label htmlFor="body" className="text-sm mt-4">
          Body
        </label>
        <textarea
          className="bg-neutral-800 border-[1px] border-neutral-300 rounded-md w-96 h-24 px-2 pt-2"
          id="body"
          disabled={submitting}
          {...register("body")}
        />
        <button
          type="submit"
          className="mt-6 bg-pink-300 text-white h-10 font-semibold rounded-md duration-500 hover:opacity-75"
        >
          Create Post
        </button>
      </form>
    </Container>
  );
};

export default CreatePostClient;
