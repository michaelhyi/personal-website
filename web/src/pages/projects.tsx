import Link from "next/link";
import { AiOutlineArrowLeft } from "react-icons/ai";
import Container from "../components/Container";
import Project from "../components/projects/Project";
import { projects } from "../data/projects";

const Projects = () => {
  return (
    <Container>
      <Link
        href="/"
        className="flex items-center gap-2 absolute top-6 left-6 text-sm cursor-pointer duration-300 hover:opacity-50 text-blue-500"
      >
        <AiOutlineArrowLeft /> Back to Home
      </Link>
      <div className="mt-36 text-3xl font-bold">Projects</div>
      <br />
      <div className="flex flex-col gap-4">
        {projects.map((v) => (
          <Project
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
