import { Experience } from "types";
import { readAllExperiences } from "services";
import Page from "@/components/Page";

const Experience = async () => {
  const data: Experience[] = await readAllExperiences();

  return <Page title="Experience" data={data} />;
};

export default Experience;
