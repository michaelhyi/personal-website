import Container from "@/components/Container";
import BackButton from "@/components/BackButton";
import type { Project } from "@/types/project";
import { projects } from "@/data/projects";
import Card from "@/components/Card";

export default function Home() {
  return (
    <Container>
      <BackButton href="/" text="Home" />
      <div className="mt-10 flex flex-col gap-8">
        {projects.map((v: Project) => (
          <Card
            date={v.date}
            desc={v.description}
            href={v.href}
            img={v.image}
            key={v.name}
            name={v.name}
            tech={v.tech}
          />
        ))}
      </div>
    </Container>
  );
}
