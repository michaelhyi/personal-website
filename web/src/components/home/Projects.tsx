import Link from "next/link";
import { projects } from "../../data/projects";
import ProjectCard from "../ProjectCard";

const Projects = () => {
  return (
    <div className="mt-12">
      <div className="flex items-center justify-between">
        <div className="text-2xl font-bold">Projects</div>
        <Link
          href="/projects"
          className="text-pink-300 cursor-pointer duration-500 hover:opacity-50 font-bold"
        >
          See All
        </Link>
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
