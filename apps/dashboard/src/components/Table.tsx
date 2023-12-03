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
import { AiOutlineClose, AiOutlineCloudDownload } from "react-icons/ai";
import {
  createPost,
  createPostImage,
  deletePost,
  readPost,
  readPostImageBytes,
  updatePost,
} from "services";
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
  const [file, setFile] = useState<FormData | null>(null);
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
      setFile(formData);
      setSubmitting(false);
    },
    [setValue, setSubmitting],
  );

  const handleImageDelete = useCallback(() => {
    setFile(null);
  }, [setFile]);

  const { getRootProps, getInputProps } = useDropzone({
    accept: {
      "image/webp": [".webp"],
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
          className="flex flex-col items-center justify-center border-2 border-neutral-300 border-dashed cursor-pointer duration-500 hover:opacity-50 rounded-lg shadow-lg h-64"
        >
          <input {...getInputProps({ name: "file" })} disabled={submitting} />
          <AiOutlineCloudDownload size={48} />
          <div>Drag & drop files here, or click to select files</div>
        </div>
        {file !== null && (
          <div className="flex items-center gap-2 text-xs mt-6">
            <div>{(file.get("file") as File).name}</div>
            <AiOutlineClose
              onClick={handleImageDelete}
              className="cursor-pointer duration-500 hover:opacity-50"
            />
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
        setFile(null);
        setModalOpen(false);
        setModalTitle(null);
        reset();
      } else if (t) {
        if (t.includes("Edit") && i) {
          const post: Post | null = await readPost(i.toString());
          const image: ArrayBuffer | null = await readPostImageBytes(
            i.toString(),
          );

          if (post && image) {
            const formData = new FormData();
            formData.append("file", new File([image], `${post.title}.jpg`));

            setFile(formData);
            setValue("title", post.title);
            setValue("image", formData);
            setValue("description", post.description);
            setValue("body", post.body);
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

      const postId = await createPost({
        title: formData.title as string,
        description: formData.description as string,
        body: formData.body as string,
      });

      if (file) {
        await createPostImage(postId, file);
      }

      setMenuOpen(new Array(data.length).fill(false));
      await handleToggleModal();
      router.refresh();
      setSubmitting(false);
    },
    [setSubmitting, file, setMenuOpen, data.length, handleToggleModal, router],
  );

  const handleUpdate: SubmitHandler<FieldValues> = useCallback(
    async (formData) => {
      setSubmitting(true);

      if (id) {
        await updatePost(id.toString(), formData);

        if (file) await createPostImage(id, file);
      }

      setMenuOpen(new Array(data.length).fill(false));
      await handleToggleModal();
      router.refresh();
      setSubmitting(false);
    },

    [
      id,
      file,
      setSubmitting,
      setMenuOpen,
      data.length,
      handleToggleModal,
      router,
    ],
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
