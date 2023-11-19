import { notFound } from "next/navigation";
import { readExperience } from "services";
import type { Experience } from "types";
import EditExperienceClient from "./client";

const EditExperience = async ({ params }: { params: { id: string } }) => {
  const data: Experience | null = await readExperience(params.id);

  if (!data) {
    notFound();
  }

  return <EditExperienceClient data={data} />;
};

export default EditExperience;
