import { readAllProjects } from "services";
import type { Project } from "types";
import Page from "@/components/Page";

const Projects = async () => {
  const data: Project[] = await readAllProjects();

  return <Page title="Projects" data={data} />;
};

export default Projects;
