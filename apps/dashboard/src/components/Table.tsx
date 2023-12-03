"use client";

import { useRouter } from "next/navigation";
import { useCallback, useState, type FC } from "react";
import { useDropzone } from "react-dropzone";
import {
  useForm,
  type FieldValues,
  type SubmitHandler,
  type UseFormRegister,
} from "react-hook-form";
import {
  AiFillDelete,
  AiFillFileText,
  AiOutlineCloudDownload,
} from "react-icons/ai";
import { createPost, deletePost, readPost, updatePost } from "services";
import type { Post } from "types";
import Container from "@/components/Container";
import { capitalize } from "@/utils/capitialize";
import Modal from "./Modal";
import TableHead from "./TableHead";
import TableBody from "./TableBody";
import Input from "./Input";

interface Props {
  data: Post[];
}

const Table: FC<Props> = ({ data }) => {
  const router = useRouter();

  const [id, setId] = useState<number | null>(null);
  const [file, setFile] = useState<File | null>(null);
  const [modalOpen, setModalOpen] = useState<boolean>(false);
  const [modalTitle, setModalTitle] = useState<string | null>(null);

  const [menuOpen, setMenuOpen] = useState<boolean[]>(
    new Array(data.length).fill(false),
  );

  const [submitting, setSubmitting] = useState<boolean>(false);

  const { register, handleSubmit, reset, setValue } = useForm({
    defaultValues: {
      title: "",
      image: new FormData(),
      description: "",
      body: "",
    },
  });

  const onDrop = useCallback(
    (acceptedFiles: File[]) => {
      const formData = new FormData();
      formData.append("file", acceptedFiles[0]);

      setValue("image", formData);
      setFile(acceptedFiles[0]);
      setSubmitting(false);
    },
    [setValue, setSubmitting],
  );

  const handleImageDelete = useCallback(() => {
    setFile(null);
  }, [setFile]);

  const { getRootProps, getInputProps } = useDropzone({
    accept: {
      "image/jpeg": [".jpeg", ".jpg"],
      "image/png": [".png"],
    },
    maxFiles: 1,
    onDrop,
  });

  const body = (
    <div className="flex flex-col gap-4">
      <Input
        id="title"
        register={register as unknown as UseFormRegister<FieldValues>}
        submitting={submitting}
      />
      <label htmlFor="image" className="font-semibold text-sm">
        {capitalize("image")}
      </label>
      <div id="image" className="flex flex-col w-96">
        <div
          {...getRootProps({ className: "dropzone" })}
          className="flex flex-col items-center justify-center border-2 border-neutral-200 border-dashed cursor-pointer duration-500 hover:opacity-50 rounded-lg shadow-lg h-64"
        >
          <input {...getInputProps({ name: "file" })} disabled={submitting} />
          <AiOutlineCloudDownload size={48} />
          <div>Drag & drop files here, or click to select files</div>
        </div>
        <br />
        <br />
        {file !== null && (
          <div className="relative flex flex-col">
            <div className="font-bold text-2xl">Uploaded File</div>
            <br />
            <AiFillDelete
              onClick={handleImageDelete}
              className="absolute top-[46px] left-[117px] text-red-400 cursor-pointer duration-500 hover:opacity-50"
              size={20}
            />
            <div className="flex flex-col text-center gap-2 items-center justify-center bg-white shadow-lg w-32 h-32 border-dashed border">
              <AiFillFileText size={48} />
              <div className="text-xs">{file.name}</div>
            </div>
          </div>
        )}
      </div>
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
            const formData = new FormData();
            formData.append(
              "file",
              new File([new Uint8Array(item.image)], `${item.title}.png`),
            );

            setValue("title", item.title);
            setValue("image", formData);
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
        <div className="font-bold text-3xl">Blog</div>
        <div className="mt-12 border-[1px] rounded-md border-neutral-500 text-sm mx-auto bg-neutral-800">
          <table className="text-left">
            <TableHead handleToggleModal={handleToggleModal} />
            <TableBody
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
