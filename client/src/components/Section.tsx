"use client";

import Link from "next/link";
import { FC, ReactNode } from "react";
import { FiArrowUpRight } from "react-icons/fi";

interface Props {
   title: string;
   children: ReactNode;
   nonDesc?: boolean;
   href?: string;
}

const Section: FC<Props> = ({ title, children, nonDesc = false, href }) => {
   return (
      <div className="mt-10">
         <div className="font-normal">
            {href ? (
               <Link
                  href={href}
                  className="flex
                                  gap-1
                                  font-normal
                                  text-sm
                                  cursor-pointer
                                  duration-500
                                  hover:opacity-50"
               >
                  {title}
                  {<FiArrowUpRight size={12} />}
               </Link>
            ) : (
               title
            )}
         </div>
         <div className={`mt-6 flex flex-col ${nonDesc ? "gap-4" : "gap-8"}`}>
            {children}
         </div>
      </div>
   );
};

export default Section;
