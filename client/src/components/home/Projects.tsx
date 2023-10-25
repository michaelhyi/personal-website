"use client";

import { projects } from "@/data/projects";
import ProjectCard from "../ProjectCard";
import Section from "../Section";

const Projects = () => {
   return (
      <Section title="Projects">
         {projects.map((v, i) => (
            <ProjectCard
               key={v.name}
               href={v.href}
               desc={v.desc}
               name={v.name}
               tech={v.tech}
               date={v.date}
               img={v.img}
            />
         ))}
      </Section>
   );
};

export default Projects;
