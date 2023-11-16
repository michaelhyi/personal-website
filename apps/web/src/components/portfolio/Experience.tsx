"use client";

import type { Experience as ExperienceType } from "types";
import { experience } from "@/data/experience";
import Card from "../Card";
import Section from "../Section";

const Experience = () => {
  return (
    <Section title="Experience">
      {experience.map((v: ExperienceType) => (
        <Card date={v.date} desc={v.description} key={v.name} name={v.name} />
      ))}
    </Section>
  );
};

export default Experience;
