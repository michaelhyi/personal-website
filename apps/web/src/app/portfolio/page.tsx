import { BackButton, Container } from "@personal-website/ui";
import Education from "@/components/portfolio/Education";
import Experience from "@/components/portfolio/Experience";
import Projects from "@/components/portfolio/Projects";
import { experience } from "@/data/experience";
import { projects } from "@/data/projects";

export default function Home() {
  return (
    <Container>
      <BackButton href="/" text="Home" />
      <div className="mt-8 font-bold text-3xl">Portfolio</div>
      <div className="mt-2 text-sm font-medium text-neutral-400">
        An overview of my professional experience, education, and projects as a
        software engineer.
      </div>
      <Experience data={experience} />
      <Education />
      <Projects data={projects} />
    </Container>
  );
}
