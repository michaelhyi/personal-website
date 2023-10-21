"use client";

import { ReactNode } from "react";

interface Props {
  href: string;
  children: ReactNode;
}

const Link: React.FC<Props> = ({ href, children }) => {
  return (
    <a
      className="text-pink-300 cursor-pointer duration-500 hover:opacity-50 font-bold"
      rel="noopener noreferrer"
      target="_blank"
      href={href}
    >
      {children}
    </a>
  );
};

export default Link;
