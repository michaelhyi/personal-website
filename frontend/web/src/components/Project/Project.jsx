import "./Project.css";

import FiArrowUpRight from "../Icons/FiArrowUpRight";
import Hoverable from "../Hoverable/Hoverable";
import NewTabLink from "../NewTabLink/NewTabLink";

export default function Project({ project }) {
    return (
        <Hoverable key={project.name}>
            <NewTabLink className="project-link" href={project.href}>
                <p className="project-date">{project.date}</p>
                <section
                    className="project-details"
                >
                    <h3 className="project-name">
                        {project.name}
                        <span className="project-name-arrow">
                            <FiArrowUpRight />
                        </span>
                    </h3>
                    <p className="project-description">{project.description}</p>
                    <p className="project-tech">{project.tech}</p>
                    {project.image && (
                        <img
                            className="project-img"
                            src={project.image}
                            alt={project.name}
                            width={200}
                            height={120}
                        />
                    )}
                </section>
            </NewTabLink>
        </Hoverable>
    );
}
