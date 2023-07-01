"use client";

import { projects } from "@/app/data/projects";

const Projects = () => {
  return (
    <div>
      <div className="font-bold text-2xl">Projects</div>
      <br />
      <div className="flex flex-col gap-4">
        {projects.map((v) => (
          <div
            className="p-4 shadow-md cursor-pointer duration-300 hover:shadow-lg hover:bg-neutral-50"
            key={v.name}
          >
            <div className="font-bold">{v.name}</div>
            <div className="text-sm">{v.tech}</div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Projects;
