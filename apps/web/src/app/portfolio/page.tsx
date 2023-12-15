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
      <Experience data={experience} />
      <Education />
      <Projects data={projects} />
    </Container>
  );
}
