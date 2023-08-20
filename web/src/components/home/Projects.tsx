import { projects } from "../../data/projects";
import ProjectCard from "../ProjectCard";
import ArrowLink from "../links/ArrowLink";

const Projects = () => {
  return (
    <div className="mt-12">
      <div className="flex items-center justify-between">
        <div className="text-xl font-semibold">Projects</div>
        <ArrowLink href="/projects" left={false} text="See All" />
      </div>
      <div className="flex flex-col gap-10 mt-12">
        {projects.map((v, i) => {
          if (i <= 3)
            return (
              <ProjectCard
                key={v.name}
                href={v.href}
                desc={v.desc}
                name={v.name}
                tech={v.tech}
                img={v.img}
              />
            );

          return <></>;
        })}
      </div>
    </div>
  );
};

export default Projects;
