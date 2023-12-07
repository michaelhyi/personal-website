"use client";

import Image from "next/image";
import { FiArrowUpRight } from "react-icons/fi";

export default function Card({
  name,
  date,
  desc,
  tech,
  img,
  href,
  blog = false,
}: {
  name: string;
  date: string;
  desc?: string;
  tech?: string;
  img?: string;
  href?: string;
  blog?: boolean;
}) {
  return (
    <div className="flex sm:flex-col md:flex-row">
      <div className="w-48 text-[13px] font-light text-neutral-400 sm:mb-2 md:mb-0">
        {date}
      </div>
      <div className="sm:w-72 md:w-96">
        <a
          rel={blog ? "" : "noopener noreferrer"}
          target={blog ? "" : "_blank"}
          href={href}
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
          {href !== undefined && <FiArrowUpRight size={12} />}
        </a>
        {desc !== undefined && (
          <div className="mt-2 text-xs text-neutral-400">{desc}</div>
        )}
        {tech !== undefined && (
          <div className="mt-2 text-[10px] text-neutral-300">{tech}</div>
        )}
        {img !== undefined && img.length !== 0 && (
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
}
