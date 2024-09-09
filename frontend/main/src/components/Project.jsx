import "../css/project.css";

export default function Project({ project }) {
    return (
        <a
            className="project-card"
            rel="noopener noreferrer"
            target="_blank"
            href={project.href}
        >
            <p className="project-date">{project.date}</p>
            <section className="project-details">
                <h3 className="project-name">
                    {project.name}&nbsp;
                    <span className="project-arrow">&#8599;</span>
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
        </a>
    );
}
