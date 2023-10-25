"use client";

import Image from "next/image";
import { FC } from "react";

interface Props {
   name: string;
   date: string;
   desc: string;
   href: string;
   tech?: string;
   img?: string;
}

const Card: FC<Props> = ({ name, date, desc, href, tech, img }) => {
   return (
      <a
         rel="noopener noreferrer"
         target="_blank"
         className="flex cursor-pointer duration-500 hover:opacity-50 sm:flex-col md:flex-row"
         href={href}
      >
         <div className="font-light text-[13px] text-neutral-400 w-48">
            {date}
         </div>
         <div className="w-96">
            <div className="font-normal text-sm">{name}</div>
            <div className="text-xs mt-2 text-neutral-500">{desc}</div>
            {tech && (
               <div className="mt-2 text-[10px] text-neutral-400">{tech}</div>
            )}
            {img && (
               <Image
                  className="mt-4 
                                 border-neutral-300 
                                 border-[1px] 
                                 rounded-lg 
                                 shadow-md"
                  src={img}
                  alt={name}
                  width={200}
                  height={120}
               />
            )}
         </div>
      </a>
   );
};

export default Card;
