"use client";

import { useRouter } from "next/navigation";
import { useCallback, useState, type FC } from "react";
import {
  useForm,
  type FieldValues,
  type SubmitHandler,
  type UseFormRegister,
} from "react-hook-form";
import { createPost, deletePost, readPost, updatePost } from "services";
import type { Post } from "types";
import Container from "@/components/Container";
import Modal from "./Modal";
import TableHead from "./TableHead";
import TableBody from "./TableBody";
import Input from "./Input";

interface Props {
  title: string;
  data: Post[];
}

const Table: FC<Props> = ({ title, data }) => {
  const router = useRouter();

  const [id, setId] = useState<number | null>(null);
  const [modalOpen, setModalOpen] = useState<boolean>(false);
  const [modalTitle, setModalTitle] = useState<string | null>(null);

  const [menuOpen, setMenuOpen] = useState<boolean[]>(
    new Array(data.length).fill(false),
  );

  const [submitting, setSubmitting] = useState<boolean>(false);

  const { register, handleSubmit, reset, setValue } = useForm({
    defaultValues: {
      title: "",
      image: "",
      description: "",
      body: "",
    },
  });

  const body = (
    <div className="flex flex-col gap-4">
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
    </div>
  );

  const handleToggleMenu = useCallback(
    (index: number) => {
      setMenuOpen(
        menuOpen.map((v, i) => {
          if (i === index) return !v;
          return false;
        }),
      );
    },
    [setMenuOpen, menuOpen],
  );

  const handleToggleModal = useCallback(
    async (t?: string, i?: number) => {
      setMenuOpen(new Array(data.length).fill(false));

      if (modalOpen) {
        setId(null);
        setModalOpen(false);
        setModalTitle(null);
        reset();
      } else if (t) {
        if (t.includes("Edit") && i) {
          const item: Post | null = await readPost(i.toString());

          if (item) {
            setValue("title", item.title);
            setValue("image", item.image);
            setValue("description", item.description);
            setValue("body", item.body);
          }
        }

        setModalOpen(true);
        setModalTitle(t);
      }

      if (i) {
        setId(i);
      }
    },
    [
      data.length,
      modalOpen,
      setId,
      setModalOpen,
      setModalTitle,
      reset,
      setValue,
    ],
  );

  const handleCreate: SubmitHandler<FieldValues> = useCallback(
    async (formData) => {
      setSubmitting(true);

      await createPost(formData);

      setMenuOpen(new Array(data.length).fill(false));
      await handleToggleModal();
      router.refresh();
      setSubmitting(false);
    },
    [setSubmitting, setMenuOpen, data.length, handleToggleModal, router],
  );

  const handleUpdate: SubmitHandler<FieldValues> = useCallback(
    async (formData) => {
      setSubmitting(true);

      if (id) {
        await updatePost(id.toString(), formData);
      }

      setMenuOpen(new Array(data.length).fill(false));
      await handleToggleModal();
      router.refresh();
      setSubmitting(false);
    },

    [id, setSubmitting, setMenuOpen, data.length, handleToggleModal, router],
  );

  const handleDelete = useCallback(async () => {
    setSubmitting(true);
    if (id) {
      await deletePost(id);
    }

    setMenuOpen(new Array(data.length).fill(false));
    await handleToggleModal();
    router.refresh();
    setSubmitting(false);
  }, [setSubmitting, id, setMenuOpen, data.length, handleToggleModal, router]);

  return (
    <Container>
      <div className="flex flex-col">
        <div className="font-bold text-3xl">{title}</div>
        <div className="mt-12 border-[1px] rounded-md border-neutral-500 text-sm mx-auto bg-neutral-800">
          <table className="text-left">
            <TableHead title={title} handleToggleModal={handleToggleModal} />
            <TableBody
              title={title}
              data={data}
              menuOpen={menuOpen}
              handleToggleMenu={handleToggleMenu}
              handleToggleModal={handleToggleModal}
            />
          </table>
        </div>
        {modalOpen && modalTitle?.includes("Create") ? (
          <Modal
            title={modalTitle}
            body={body}
            modalOpen={modalOpen}
            handleToggleModal={handleToggleModal}
            action={handleCreate}
            actionLabel="Submit"
            handleSubmit={handleSubmit}
          />
        ) : null}
        {modalOpen && modalTitle?.includes("Edit") ? (
          <Modal
            title={modalTitle}
            body={body}
            modalOpen={modalOpen}
            handleToggleModal={handleToggleModal}
            action={handleSubmit(handleUpdate)}
            actionLabel="Submit"
            handleSubmit={handleSubmit}
          />
        ) : null}
        {modalOpen && modalTitle?.includes("Delete") ? (
          <Modal
            title={modalTitle}
            body={body}
            modalOpen={modalOpen}
            handleToggleModal={handleToggleModal}
            action={handleDelete}
            actionLabel="Delete"
          />
        ) : null}
      </div>
    </Container>
  );
};

export default Table;
