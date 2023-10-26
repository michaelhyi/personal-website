"use client";

import { projects } from "@/data/projects";
import Card from "../Card";
import Section from "../Section";

const Projects = () => {
   return (
      <Section title="Projects" href="https://michael-yi.com/Resume.pdf">
         {projects.map((v, i) => {
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
         })}
      </Section>
   );
};

export default Projects;
