"use client";

import Image from "next/image";
import Link from "next/link";
import Hoverable from "@/components/Hoverable";

export default function Card({
  name,
  date,
  desc,
  tech,
  img,
  href,
}: {
  name: string;
  date: string;
  desc?: string;
  tech?: string;
  img?: string;
  href: string;
}) {
  return (
    <Hoverable>
      <Link rel="noopener noreferre" target="_blank" href={href}>
        <div className="flex sm:flex-col md:flex-row">
          <div className="w-56 text-[13px] font-light text-neutral-400 sm:mb-2 md:mb-0">
            {date}
          </div>
          <div className="w-full">
            <div className="flex gap-1 text-sm font-medium">{name}</div>
            <div className="mt-2 text-xs text-neutral-400">{desc}</div>
            <div className="mt-2 text-[10px] text-neutral-300 font-light">
              {tech}
            </div>
            {img !== undefined && img.length !== 0 && (
              <Image
                className="mt-4 rounded-md shadow-md"
                src={img}
                alt={name}
                width={200}
                height={120}
              />
            )}
          </div>
        </div>
      </Link>
    </Hoverable>
  );
}
