import "./portfolio.css";

import { BackButton, Container, Hoverable, Project } from "../../components";
import FiArrowUpRight from "../../components/Icons/FiArrowUpRight";
import HACKATHONS from "../../utils/constants/hackathons";
import PROJECTS from "../../utils/constants/projects";

export default function Home() {
    return (
        <Container>
            <BackButton href="/" text="Home" />
            <section className="portfolio-projects">
                <h1 className="portfolio-projects-header">Projects</h1>
                {PROJECTS.map((project) => (
                    <Project key={project.name} project={project} />
                ))}
            </section>
            <section className="portfolio-hackathons">
                <h1 className="portfolio-hackathons-header">Hackathons</h1>
                {HACKATHONS.map((hackathon) => (
                    <Hoverable
                        className="portfolio-hackathons-card"
                        key={hackathon.name}
                    >
                        <a
                            className="portfolio-hackathons-card-anchor"
                            href={hackathon.href}
                        >
                            {hackathon.name}
                            <span className="portfolio-hackathons-card-anchor-arrow ">
                                <FiArrowUpRight />
                            </span>
                        </a>
                    </Hoverable>
                ))}
            </section>
        </Container>
    );
}
