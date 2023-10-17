import Container from "../components/Container";
import ProjectCard from "../components/ProjectCard";
import ArrowLink from "../components/links/ArrowLink";
import { projects } from "../data/projects";

const Projects = () => {
  return (
    <Container>
      <div className="mt-24" />
      <ArrowLink href="/" left text="Home" />
      <div className="text-xl font-semibold mt-12">Projects</div>
      <div className="flex flex-col gap-10 mt-12">
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
