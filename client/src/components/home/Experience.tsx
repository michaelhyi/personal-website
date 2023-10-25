"use client";

import { experience } from "@/data/experience";
import Section from "../Section";
import Card from "@/components/Card";

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
               img={v.img}
            />
         ))}
      </Section>
   );
};

export default Experience;
