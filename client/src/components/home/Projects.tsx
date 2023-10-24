"use client";

import { projects } from "@/data/projects";
import ProjectCard from "../ProjectCard";
import ArrowLink from "../links/ArrowLink";

const Projects = () => {
   return (
      <div className="mt-12">
         <div className="flex items-center justify-between">
            <div className="font-semibold sm:text-lg md:text-xl">Projects</div>
            <ArrowLink href="/projects" left={false} text="See All" />
         </div>
         <div className="mt-12 flex flex-col gap-10">
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
