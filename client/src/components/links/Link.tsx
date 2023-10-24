"use client";

import { FC, ReactNode } from "react";

interface Props {
   href: string;
   children: ReactNode;
}

const Link: FC<Props> = ({ href, children }) => {
   return (
      <a
         className="cursor-pointer font-bold text-pink-300 duration-500 hover:opacity-50"
         rel="noopener noreferrer"
         target="_blank"
         href={href}
      >
         {children}
      </a>
   );
};

export default Link;
