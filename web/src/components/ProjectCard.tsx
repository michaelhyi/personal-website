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
      className="flex gap-4 cursor-pointer duration-300 hover:opacity-50"
      href={href}
    >
      {img && (
        <Image
          unoptimized={true}
          src={img}
          alt={name}
          width={160}
          height={120}
        />
      )}
      <div>
        <div className="font-semibold">{name}</div>
        <div className="text-sm">{desc}</div>
        <div className="text-xs mt-2 opacity-75">{tech}</div>
      </div>
    </a>
  );
};

export default ProjectCard;
