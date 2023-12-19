"use client";

import Link from "next/link";
import type { ReactNode } from "react";
import { FiArrowUpRight } from "react-icons/fi";

export default function Section({
  title,
  children,
  href,
}: {
  title: string;
  children: ReactNode;
  href?: string;
}) {
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
      <div className="mt-6 flex flex-col gap-8">{children}</div>
    </div>
  );
}
