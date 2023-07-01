"use client";

interface Props {
  name: string;
  href: string;
  desc: string;
  tech: string;
}

const Project: React.FC<Props> = ({ name, href, desc, tech }) => {
  return (
    <a
      rel="noopener noreferrer"
      target="_blank"
      className="p-4 shadow-md cursor-pointer duration-300 hover:shadow-lg hover:bg-neutral-50"
      href={href}
    >
      <div className="font-bold text-lg">{name}</div>
      <div className="text-sm">{desc}</div>
      <div className="text-xs mt-2 opacity-75">{tech}</div>
    </a>
  );
};

export default Project;
