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
            <div className="font-bold text-lg">{v.name}</div>
            <div className="text-sm">{v.desc}</div>
            <div className="text-xs mt-2 opacity-75">{v.tech}</div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Projects;
