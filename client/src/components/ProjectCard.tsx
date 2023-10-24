"use client";

import Image from "next/image";
import { FC } from "react";

interface Props {
   name: string;
   href: string;
   desc: string;
   tech: string;
   img?: string;
}

const ProjectCard: FC<Props> = ({ name, href, desc, tech, img }) => {
   return (
      <a
         rel="noopener noreferrer"
         target="_blank"
         className="flex cursor-pointer gap-4 duration-500 hover:opacity-50 sm:flex-col md:flex-row"
         href={href}
      >
         {img && <Image src={img} alt={name} width={200} height={120} />}
         <div>
            <div className="font-semibold">{name}</div>
            <div>{desc}</div>
            <div className="mt-2 text-xs opacity-75">{tech}</div>
         </div>
      </a>
   );
};

export default ProjectCard;
