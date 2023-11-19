"use client";

import type { FC } from "react";
import type { Project } from "types";
import Card from "../Card";
import Section from "../Section";

interface Props {
  data: Project[];
}

const Projects: FC<Props> = ({ data }) => {
  return (
    <Section title="Projects">
      {data.map((v: Project) => (
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
