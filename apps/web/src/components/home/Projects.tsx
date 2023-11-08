"use client";

import { projects } from "@/data/projects";
import type Project from "@/types/project";
import Card from "../Card";
import Section from "../Section";

const Projects = () => {
  return (
    <Section title="Projects">
      {projects.map((v: Project) => (
        <Card
          date={v.date}
          desc={v.desc}
          href={v.href}
          img={v.img}
          key={v.name}
          name={v.name}
          tech={v.tech}
        />
      ))}
    </Section>
  );
};

export default Projects;
