"use client";

import { projects } from "@/data/projects";
import Card from "../Card";
import Section from "../Section";

interface Props {
   extended?: boolean;
}

const Projects: React.FC<Props> = ({ extended = false }) => {
   return (
      <Section title="Projects" href={extended ? undefined : "/projects"}>
         {projects.map((v, i) => {
            if (!extended) {
               if (i <= 4)
                  return (
                     <Card
                        key={v.name}
                        href={v.href}
                        desc={v.desc}
                        name={v.name}
                        tech={v.tech}
                        date={v.date}
                        img={v.img}
                     />
                  );

               return;
            }

            return (
               <Card
                  key={v.name}
                  href={v.href}
                  desc={v.desc}
                  name={v.name}
                  tech={v.tech}
                  date={v.date}
                  img={v.img}
               />
            );
         })}
      </Section>
   );
};

export default Projects;
