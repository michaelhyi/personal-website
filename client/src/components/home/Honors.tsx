"use client";

import { honors } from "@/data/honors";
import Section from "../Section";
import Card from "@/components/Card";

const Honors = () => {
   return (
      <Section title="Honors" nonDesc>
         {honors.map((v) => (
            <Card key={v.name}
                   name={v.name}
                   date={v.date}
                   />
         ))}
      </Section>
   );
};

export default Honors;
