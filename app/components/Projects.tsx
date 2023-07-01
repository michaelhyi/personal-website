"use client";

import { projects } from "@/app/data/projects";
import Link from "next/link";

const Projects = () => {
  return (
    <div>
      <div className="flex items-center justify-between">
        <div className="font-bold text-2xl">Projects</div>
        <Link
          href="/projects"
          className="text-sm text-blue-500 cursor-pointer duration-500 hover:opacity-50"
        >
          See All
        </Link>
      </div>
      <br />
      <div className="flex flex-col gap-4">
        {projects.map((v, i) => {
          if (i <= 3)
            return (
              <a
                rel="noopener noreferrer"
                target="_blank"
                className="p-4 shadow-md cursor-pointer duration-300 hover:shadow-lg hover:bg-neutral-50"
                key={v.name}
                href={v.href}
              >
                <div className="font-bold text-lg">{v.name}</div>
                <div className="text-sm">{v.desc}</div>
                <div className="text-xs mt-2 opacity-75">{v.tech}</div>
              </a>
            );

          return <></>;
        })}
      </div>
    </div>
  );
};

export default Projects;
