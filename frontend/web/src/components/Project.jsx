import Hoverable from "./Hoverable";
import NewTabLink from "./NewTabLink";
import FiArrowUpRight from "../assets/icons/FiArrowUpRight";

export default function Project({ project }) {
    return (
        <Hoverable key={project.name}>
            <NewTabLink
                className="text-left flex sm:flex-col md:flex-row"
                href={project.href}
            >
                <p className="w-56 text-[13px] font-light text-neutral-400 sm:mb-2 md:mb-0">
                    {project.date}
                </p>
                <section className="w-full">
                    <h3 className="font-semibold">
                        {project.name}
                        <span className="inline-block">
                            <FiArrowUpRight />
                        </span>
                    </h3>
                    <p className="mt-1 text-xs text-neutral-400">
                        {project.description}
                    </p>
                    <p className="mt-2 text-[10px] text-neutral-300 font-light">
                        {project.tech}
                    </p>
                    {project.image && (
                        <img
                            className="mt-4 rounded-md shadow-md"
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
