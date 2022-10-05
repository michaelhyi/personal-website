import { projects } from "../data/projects";
import Project from "./Project";

const Projects = () => {
  return (
    <div
      id="projects"
      className="pt-8 sm:flex sm:flex-col sm:items-center md:items-start"
    >
      <div className="font-bold text-5xl mt-24 mb-16">Projects</div>
      <div className="grid lg:grid-cols-1 xl:grid-cols-2 gap-8 md:m-auto">
        {projects.map((v, i) => (
          <Project key={i} data={v} />
        ))}
      </div>
    </div>
  );
};

export default Projects;
