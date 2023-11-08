"use client";

import type { FC } from "react";
import { projects } from "@/data/projects";
import type Project from "@/types/project";
import Card from "../Card";
import Section from "../Section";

interface Props {
  extended?: boolean;
}

const Projects: FC<Props> = ({ extended = false }) => {
  return (
    <Section href={extended ? undefined : "/projects"} title="Projects">
      {projects.map((v: Project, i) => {
        if (!extended) {
          if (i < 3)
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
