import { readAllExperiences } from "services";
import type { Experience as ExperienceType } from "types";
import ExperienceTable from "@/components/ExperienceTable";

const Experience = async () => {
  const data: ExperienceType[] = await readAllExperiences();

  return <ExperienceTable title="Experience" data={data} />;
};

export default Experience;
