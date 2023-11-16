"use client";

import type { AxiosError } from "axios";
import { useState, useCallback } from "react";
import { useForm } from "react-hook-form";
import type { FieldValues, SubmitHandler } from "react-hook-form";
import { FaArrowLeft } from "react-icons/fa";
import Link from "next/link";
import Container from "@/components/Container";
import Modal from "@/components/Modal";
import { updatePost } from "@/services/post";
import type Post from "@/types/post";

interface Props {
  data: Post;
}

const EditPostClient: React.FC<Props> = ({ data }) => {
  // const [error, setError] = useState<string | null>(null);
  const [submitting, setSubmitting] = useState<boolean>(false);
  const [showModal, setShowModal] = useState<boolean>(false);

  const { register, handleSubmit, setValue } = useForm({
    defaultValues: {
      title: data.title,
      image: data.image,
      description: data.description,
      body: data.body,
    },
  });

  const handleToggle = useCallback(() => {
    setShowModal(!showModal);
  }, [setShowModal, showModal]);

  const handleUpdatePost: SubmitHandler<FieldValues> = useCallback(
    async (formData) => {
      setSubmitting(true);

      try {
        await updatePost(data.id.toString(), formData);

        setValue("title", "");
        setValue("image", "");
        setValue("description", "");
        setValue("body", "");
      } catch (e) {
        const { response } = e as AxiosError;

        if (response) {
          // setError(response.data as string);
        }
      } finally {
        setSubmitting(false);
      }
    },
    [data.id, setValue],
  );

  return (
    <Container>
      <Link
        href="/dashboard/blog"
        className="text-neutral-300 duration-500 hover:opacity-50"
      >
        <FaArrowLeft />
      </Link>
      <div className="font-bold text-3xl mt-8">Edit Post</div>
      <form
        className="mt-8 flex flex-col gap-2"
        onSubmit={handleSubmit(handleUpdatePost)}
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
          Edit Post
        </button>
        <button
          onClick={handleToggle}
          type="button"
          className="mt-3 bg-red-400 text-white h-10 font-semibold rounded-md duration-500 hover:opacity-75"
        >
          Delete Post
        </button>
      </form>
      <Modal showModal={showModal} handleToggle={handleToggle} id={data.id} />
    </Container>
  );
};

export default EditPostClient;
