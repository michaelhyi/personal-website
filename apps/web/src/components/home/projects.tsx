"use client";

import type { FC } from "react";
import { projects } from "../../data/projects";
import type Project from "../../types/project";
import Card from "../card";
import Section from "../section";

interface Params {
  extended?: boolean;
}

const Projects: FC<Params> = ({ extended = false }) => {
  return (
    <Section href={extended ? undefined : "/projects"} title="Projects">
      {projects.map((v: Project, i) => {
        if (!extended) {
          if (i <= 4)
            return (
              <Card
                date={v.date}
                desc={v.desc}
                href={v.href}
                img={v.img}
                key={v.name}
                name={v.name}
                tech={v.tech}
              />
            );

          return;
        }

        return (
          <Card
            date={v.date}
            desc={v.desc}
            href={v.href}
            img={v.img}
            key={v.name}
            name={v.name}
            tech={v.tech}
          />
        );
      })}
    </Section>
  );
};

export default Projects;
