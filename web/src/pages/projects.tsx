import Link from "next/link";
import { AiOutlineArrowLeft } from "react-icons/ai";
import Container from "../components/Container";
import ProjectCard from "../components/ProjectCard";
import { projects } from "../data/projects";

const Projects = () => {
  return (
    <Container>
      <Link
        href="/"
        className="mt-24 flex items-center gap-2 text-sm cursor-pointer duration-300 hover:opacity-50 text-pink-300 font-bold"
      >
        <AiOutlineArrowLeft /> Home
      </Link>
      <br />
      <br />
      <div className="text-3xl font-bold">Projects</div>
      <br />
      <div className="flex flex-col gap-4">
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
