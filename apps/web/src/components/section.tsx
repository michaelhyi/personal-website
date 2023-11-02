"use client";

import Link from "next/link";
import type { FC, ReactNode } from "react";
import { FiArrowUpRight } from "react-icons/fi";

interface Params {
  title: string;
  children: ReactNode;
  href?: string;
  nonDesc?: boolean;
}

const Section: FC<Params> = ({ title, children, href, nonDesc = false }) => {
  return (
    <div className="mt-10">
      {href ? (
        <Link
          href={href}
          className="flex
                             cursor-pointer
                             gap-1
                             font-normal
                             duration-500
                             hover:opacity-50"
        >
          {title}
          <FiArrowUpRight size={12} />
        </Link>
      ) : (
        <div className="font-normal">{title}</div>
      )}
      <div className={`mt-6 flex flex-col ${nonDesc ? "gap-4" : "gap-8"}`}>
        {children}
      </div>
    </div>
  );
};

export default Section;
