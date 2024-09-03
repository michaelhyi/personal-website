import "../css/portfolio.css";

import BackButton from "../components/BackButton";
import Container from "../components/Container";
import Project from "../components/Project";

import FiArrowUpRight from "../components/FiArrowUpRight";
import HACKATHONS from "../util/hackathons";
import PROJECTS from "../util/projects";

export default function Portfolio() {
    return (
        <Container>
            <BackButton href="/" />
            <section className="portfolio-projects">
                <h1 className="portfolio-projects-header">Projects</h1>
                {PROJECTS.map((project) => (
                    <Project key={project.name} project={project} />
                ))}
            </section>
            <section className="portfolio-hackathons">
                <h1 className="portfolio-hackathons-header">Hackathons</h1>
                {HACKATHONS.map((hackathon) => (
                    <a
                        className="hoverable portfolio-hackathons-card-anchor"
                        key={hackathon.name}
                        href={hackathon.href}
                        rel="noopener noreferrer"
                        target="_blank"
                    >
                        {hackathon.name}
                        <span className="portfolio-hackathons-card-anchor-arrow ">
                            <FiArrowUpRight />
                        </span>
                    </a>
                ))}
            </section>
        </Container>
    );
}
