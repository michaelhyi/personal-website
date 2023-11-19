import { notFound } from "next/navigation";
import { readProject } from "services";
import type { Project } from "types";
import EditProjectClient from "./client";

const EditProject = async ({ params }: { params: { id: string } }) => {
  const data: Project | null = await readProject(params.id);

  if (!data) {
    notFound();
  }

  return <EditProjectClient data={data} />;
};

export default EditProject;
