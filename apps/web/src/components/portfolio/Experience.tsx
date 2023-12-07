"use client";

import type { Experience as ExperienceType } from "@personal-website/types";
import Card from "../Card";
import Section from "../Section";

export default function Experience({ data }: { data: ExperienceType[] }) {
  return (
    <Section title="Experience">
      {data.map((v: ExperienceType) => (
        <Card date={v.date} desc={v.description} key={v.name} name={v.name} />
      ))}
    </Section>
  );
}
