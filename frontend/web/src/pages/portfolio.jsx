import BackButton from "../components/BackButton";
import Container from "../components/Container";
import Project from "../components/Project";
import PROJECTS from "../constants/projects";

export default function Home() {
    return (
        <Container>
            <BackButton href="/" text="Home" />
            <section className="mt-10 flex flex-col gap-8">
                {PROJECTS.map((project) => (
                    <Project key={project.name} project={project} />
                ))}
            </section>
        </Container>
    );
}
