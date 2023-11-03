"use client";

import { experience } from "@/data/experience";
import Card from "../card";
import Section from "../section";

const Experience = () => {
  return (
    <Section title="Experience">
      {experience.map((v) => (
        <Card
          date={v.date}
          desc={v.desc}
          href={v.href}
          key={v.name}
          name={v.name}
        />
      ))}
    </Section>
  );
};

export default Experience;
