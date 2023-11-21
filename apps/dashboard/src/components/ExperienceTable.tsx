"use client";

import { useRouter } from "next/navigation";
import { useCallback, useState, type FC } from "react";
import {
  useForm,
  type FieldValues,
  type SubmitHandler,
  type UseFormRegister,
} from "react-hook-form";
import {
  createExperience,
  deleteExperience,
  readExperience,
  updateExperience,
} from "services";
import type { Experience } from "types";
import Container from "@/components/Container";
import Modal from "./Modal";
import TableHead from "./TableHead";
import TableBody from "./TableBody";
import Input from "./Input";

interface Props {
  title: string;
  data: Experience[];
}

const ExperienceTable: FC<Props> = ({ title, data }) => {
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
      name: "",
      date: "",
      description: "",
    },
  });

  const body = (
    <div className="flex flex-col gap-4">
      <Input
        id="name"
        register={register as unknown as UseFormRegister<FieldValues>}
        submitting={submitting}
      />
      <Input
        id="date"
        register={register as unknown as UseFormRegister<FieldValues>}
        submitting={submitting}
      />
      <Input
        id="description"
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
      if (modalOpen) {
        setId(null);
        setModalOpen(false);
        setModalTitle(null);
        reset();
      } else if (t) {
        if (t.includes("Edit") && i) {
          const item: Experience | null = await readExperience(i.toString());

          //TODO
          if (item) {
            setValue("name", item.name);
            setValue("date", item.date);
            setValue("description", item.description);
          }
        }

        setModalOpen(true);
        setModalTitle(t);
      }

      if (i) {
        setId(i);
      }
    },
    [modalOpen, setId, setModalOpen, setModalTitle, reset, setValue],
  );

  const handleCreate: SubmitHandler<FieldValues> = useCallback(
    async (formData) => {
      setSubmitting(true);

      await createExperience(formData);

      setMenuOpen([...menuOpen, false]);
      await handleToggleModal();
      router.refresh();
      setSubmitting(false);
    },
    [setSubmitting, setMenuOpen, menuOpen, handleToggleModal, router],
  );

  const handleUpdate: SubmitHandler<FieldValues> = useCallback(
    async (formData) => {
      if (id) {
        setSubmitting(true);

        await updateExperience(id.toString(), formData);

        setMenuOpen([...menuOpen, false]);
        await handleToggleModal();
        router.refresh();
        setSubmitting(false);
      }
    },
    [id, setSubmitting, setMenuOpen, menuOpen, handleToggleModal, router],
  );

  const handleDelete = useCallback(async () => {
    setSubmitting(true);

    if (id) {
      await deleteExperience(id);
    }

    setMenuOpen([...menuOpen, false]);
    await handleToggleModal();
    router.refresh();
    setSubmitting(false);
  }, [setSubmitting, id, setMenuOpen, menuOpen, handleToggleModal, router]);

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

export default ExperienceTable;
