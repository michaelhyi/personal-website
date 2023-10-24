import Container from "@/components/Container";
import ProjectCard from "@/components/ProjectCard";
import ArrowLink from "@/components/links/ArrowLink";
import { projects } from "@/data/projects";

const Projects = () => {
   return (
      <Container>
         <div className="mt-24" />
         <ArrowLink href="/" left text="Home" />
         <div className="mt-12 text-xl font-semibold">Projects</div>
         <div className="mt-12 flex flex-col gap-10">
            {projects.map((v) => (
               <ProjectCard
                  key={v.name}
                  name={v.name}
                  desc={v.desc}
                  href={v.href}
                  tech={v.tech}
               />
            ))}
         </div>
      </Container>
   );
};

export default Projects;
