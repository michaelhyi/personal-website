"use client";

import type { FC } from "react";
import type { Experience as ExperienceType } from "types";
import Card from "../Card";
import Section from "../Section";

interface Props {
  data: ExperienceType[];
}

const Experience: FC<Props> = ({ data }) => {
  return (
    <Section title="Experience">
      {data.map((v: ExperienceType) => (
        <Card date={v.date} desc={v.description} key={v.name} name={v.name} />
      ))}
    </Section>
  );
};

export default Experience;
