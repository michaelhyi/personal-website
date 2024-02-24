import BackButton from "../components/BackButton";
import Container from "../components/Container";
import Hoverable from "../components/Hoverable";
import { PROJECTS } from "../constants/projects";

export default function Home() {
  return (
    <Container>
      <BackButton href="/" text="Home" />
      <div className="mt-10 flex flex-col gap-8">
        {PROJECTS.map((project) => (
          <Hoverable key={project.name}>
            <a rel="noopener noreferrer" target="_blank" href={project.href}>
              <div className="flex sm:flex-col md:flex-row">
                <div className="w-56 text-[13px] font-light text-neutral-400 sm:mb-2 md:mb-0">
                  {project.date}
                </div>
                <div className="w-full">
                  <div className="flex gap-1 font-semibold">{project.name}</div>
                  <div className="mt-1 text-xs text-neutral-400">
                    {project.description}
                  </div>
                  <div className="mt-2 text-[10px] text-neutral-300 font-light">
                    {project.tech}
                  </div>
                  {project.image !== undefined &&
                    project.image.length !== 0 && (
                      <img
                        className="mt-4 rounded-md shadow-md"
                        src={project.image}
                        alt={project.name}
                        width={200}
                        height={120}
                      />
                    )}
                </div>
              </div>
            </a>
          </Hoverable>
        ))}
      </div>
    </Container>
  );
}
