import { readAllProjects } from "services";
import type { Project } from "types";
import ProjectTable from "@/components/ProjectTable";

const Projects = async () => {
  const data: Project[] = await readAllProjects();

  return <ProjectTable title="Projects" data={data} />;
};

export default Projects;
