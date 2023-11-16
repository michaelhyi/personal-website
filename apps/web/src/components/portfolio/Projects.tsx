"use client";

import type { Project } from "types";
import { projects } from "@/data/projects";
import Card from "../Card";
import Section from "../Section";

const Projects = () => {
  return (
    <Section title="Projects">
      {projects.map((v: Project) => (
        <Card
          date={v.date}
          desc={v.description}
          href={v.href}
          img={v.image}
          key={v.name}
          name={v.name}
          tech={v.tech}
        />
      ))}
    </Section>
  );
};

export default Projects;
