"use client";

import type { Project } from "@/types/project";
import Card from "./Card";
import { projects } from "@/data/projects";

export default function Projects() {
  return (
    <>
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
    </>
  );
}
