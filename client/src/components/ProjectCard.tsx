import Image from "next/image";

interface Props {
  name: string;
  href: string;
  desc: string;
  tech: string;
  img?: string;
}

const ProjectCard: React.FC<Props> = ({ name, href, desc, tech, img }) => {
  return (
    <a
      rel="noopener noreferrer"
      target="_blank"
      className="flex sm:flex-col md:flex-row gap-4 cursor-pointer duration-500 hover:opacity-50"
      href={href}
    >
      {img && <Image src={img} alt={name} width={200} height={120} />}
      <div>
        <div className="font-semibold">{name}</div>
        <div>{desc}</div>
        <div className="text-xs mt-2 opacity-75">{tech}</div>
      </div>
    </a>
  );
};

export default ProjectCard;
