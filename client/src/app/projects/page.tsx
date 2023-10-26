import Card from "@/components/Card";
import Container from "@/components/Container";
import Section from "@/components/Section";
import { projects } from "@/data/projects";

const Projects = () => {
   return (
      <Container>
         <Section title="Projects">
            {projects.map((v) => (
               <Card
                  key={v.name}
                  href={v.href}
                  desc={v.desc}
                  name={v.name}
                  tech={v.tech}
                  date={v.date}
                  img={v.img}
               />
            ))}
         </Section>
      </Container>
   );
};

export default Projects;
