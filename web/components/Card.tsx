"use client";

import Image from "next/image";
import { FC } from "react";
import { FiArrowUpRight } from "react-icons/fi";

interface Props {
   name: string;
   date: string;
   desc?: string;
   tech?: string;
   img?: string;
   href?: string;
}

const Card: FC<Props> = ({ name, date, desc, tech, img, href }) => {
   return (
      <div className="flex sm:flex-col md:flex-row">
         <div className="w-48 text-[13px] font-light text-neutral-400 sm:mb-2 md:mb-0">
            {date}
         </div>
         <div className="sm:w-72 md:w-96">
            <a
               rel="noopener noreferrer"
               target="_blank"
               href={href && href}
               className={`flex 
                           gap-1
                           text-sm
                           font-normal
                           ${
                              href &&
                              "cursor-pointer duration-500 hover:opacity-50"
                           }`}
            >
               {name}
               {href && <FiArrowUpRight size={12} />}
            </a>
            {desc && (
               <div className="mt-2 text-xs text-neutral-500">{desc}</div>
            )}
            {tech && (
               <div className="mt-2 text-[10px] text-neutral-400">{tech}</div>
            )}
            {img && (
               <Image
                  className="mt-4 
                                 rounded-lg 
                                 border-[1px] 
                                 border-neutral-300 
                                 shadow-md"
                  src={img}
                  alt={name}
                  width={200}
                  height={120}
               />
            )}
         </div>
      </div>
   );
};

export default Card;
