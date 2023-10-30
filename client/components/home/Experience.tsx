"use client";

import { experience } from "@/data/experience";
import Card from "../Card";
import Section from "../Section";

const Experience = () => {
   return (
      <Section title="Experience">
         {experience.map((v) => (
            <Card
               key={v.name}
               href={v.href}
               desc={v.desc}
               name={v.name}
               date={v.date}
            />
         ))}
      </Section>
   );
};

export default Experience;
