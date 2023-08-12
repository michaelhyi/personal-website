import Link from "next/link";
import { projects } from "../../data/projects";
import ProjectCard from "../ProjectCard";

const Projects = () => {
  return (
    <div>
      <div className="flex items-center justify-between">
        <div className="font-bold text-2xl">Projects</div>
        <Link
          href="/projects"
          className="text-sm text-pink-300 cursor-pointer duration-500 hover:opacity-50 font-bold"
        >
          See All
        </Link>
      </div>
      <br />
      <div className="flex flex-col gap-4">
        {projects.map((v, i) => {
          if (i <= 3)
            return (
              <ProjectCard
                key={v.name}
                href={v.href}
                desc={v.desc}
                name={v.name}
                tech={v.tech}
              />
            );

          return <></>;
        })}
      </div>
    </div>
  );
};

export default Projects;
