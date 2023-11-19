"use client";

import type { AxiosError } from "axios";
import Link from "next/link";
import { type ReactElement, useCallback, useState, type FC } from "react";
import type {
  FieldValues,
  SubmitHandler,
  UseFormRegister,
} from "react-hook-form";
import { useForm } from "react-hook-form";
import { FaArrowLeft } from "react-icons/fa";
import { deletePost, updatePost } from "services";
import type { Post } from "types";
import Container from "@/components/Container";
import Input from "@/components/Input";
import Modal from "@/components/Modal";

interface Props {
  data: Post;
}

const Body: FC = (): ReactElement => {
  return (
    <>
      <div>body</div>
      <div>body</div>
    </>
  );
};

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
        className="mt-8 flex flex-col gap-4"
        onSubmit={handleSubmit(handleUpdatePost)}
      >
        <Input
          id="title"
          register={register as unknown as UseFormRegister<FieldValues>}
          submitting={submitting}
        />
        <Input
          id="image"
          register={register as unknown as UseFormRegister<FieldValues>}
          submitting={submitting}
        />
        <Input
          id="description"
          register={register as unknown as UseFormRegister<FieldValues>}
          submitting={submitting}
        />
        <Input
          id="body"
          register={register as unknown as UseFormRegister<FieldValues>}
          submitting={submitting}
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
      <Modal
        title="Delete Post"
        description="Are you sure? This action cannot be undone."
        showModal={showModal}
        handleToggle={handleToggle}
        id={data.id}
        action={deletePost}
        callbackUrl="/blog"
        body={<Body />}
      />
    </Container>
  );
};

export default EditPostClient;
