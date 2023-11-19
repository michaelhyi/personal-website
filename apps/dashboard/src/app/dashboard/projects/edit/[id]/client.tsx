"use client";

import type { AxiosError } from "axios";
import Link from "next/link";
import { useCallback, useState } from "react";
import type { FieldValues, SubmitHandler } from "react-hook-form";
import { useForm } from "react-hook-form";
import { FaArrowLeft } from "react-icons/fa";
import { updateProject } from "services";
import type { Project } from "types";
import Container from "@/components/Container";

interface Props {
  data: Project;
}

const EditProjectClient: React.FC<Props> = ({ data }) => {
  // const [error, setError] = useState<string | null>(null);
  const [submitting, setSubmitting] = useState<boolean>(false);
  const [showModal, setShowModal] = useState<boolean>(false);

  const { register, handleSubmit, setValue } = useForm({
    defaultValues: {
      name: "",
      date: "",
      description: "",
      tech: "",
      image: "",
      href: "",
    },
  });

  const handleToggle = useCallback(() => {
    setShowModal(!showModal);
  }, [setShowModal, showModal]);

  const handleUpdateProject: SubmitHandler<FieldValues> = useCallback(
    async (formData) => {
      setSubmitting(true);

      try {
        await updateProject(data.id.toString(), formData);

        setValue("name", "");
        setValue("date", "");
        setValue("description", "");
        setValue("tech", "");
        setValue("image", "");
        setValue("href", "");
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
        href="/dashboard/projects"
        className="text-neutral-300 duration-500 hover:opacity-50"
      >
        <FaArrowLeft />
      </Link>
      <div className="font-bold text-3xl mt-8">Edit Project</div>
      <form
        className="mt-8 flex flex-col gap-2"
        onSubmit={handleSubmit(handleUpdateProject)}
      >
        <label htmlFor="name" className="text-sm">
          Name
        </label>
        <input
          className="bg-neutral-800 border-[1px] border-neutral-300 rounded-md w-96 h-10 px-2"
          id="name"
          disabled={submitting}
          {...register("name")}
        />
        <label htmlFor="date" className="text-sm mt-4">
          Date
        </label>
        <input
          className="bg-neutral-800 border-[1px] border-neutral-300 rounded-md w-96 h-10 px-2"
          id="date"
          disabled={submitting}
          {...register("date")}
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
        <label htmlFor="tech" className="text-sm">
          Tech
        </label>
        <input
          className="bg-neutral-800 border-[1px] border-neutral-300 rounded-md w-96 h-10 px-2"
          id="tech"
          disabled={submitting}
          {...register("tech")}
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
        <label htmlFor="href" className="text-sm mt-4">
          href
        </label>
        <input
          className="bg-neutral-800 border-[1px] border-neutral-300 rounded-md w-96 h-10 px-2"
          id="href"
          disabled={submitting}
          {...register("href")}
        />
        <button
          type="submit"
          className="mt-6 bg-pink-300 text-white h-10 font-semibold rounded-md duration-500 hover:opacity-75"
        >
          Edit Project
        </button>
        <button
          onClick={handleToggle}
          type="button"
          className="mt-3 bg-red-400 text-white h-10 font-semibold rounded-md duration-500 hover:opacity-75"
        >
          Delete Project
        </button>
      </form>
      {/* <Modal
        name="Delete Project"
        showModal={showModal}
        handleToggle={handleToggle}
        id={data.id}
        action={deleteProject}
        callbackUrl="/project"
      /> */}
    </Container>
  );
};

export default EditProjectClient;
