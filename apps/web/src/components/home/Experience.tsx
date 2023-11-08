"use client";

import { experience } from "@/data/experience";
import Card from "../Card";
import Section from "../Section";

const Experience = () => {
  return (
    <Section title="Experience">
      {experience.map((v) => (
        <Card date={v.date} desc={v.desc} key={v.name} name={v.name} />
      ))}
    </Section>
  );
};

export default Experience;
