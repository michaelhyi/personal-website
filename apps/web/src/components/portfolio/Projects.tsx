"use client";

import type { Project } from "@personal-website/types";
import Card from "../Card";
import Section from "../Section";

export default function Projects({ data }: { data: Project[] }) {
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
}
