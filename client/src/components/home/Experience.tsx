"use client";

import { experience } from "@/data/experience";
import ArrowLink from "../links/ArrowLink";
import Link from "../links/Link";

const Experience = () => {
   return (
      <div className="mt-12">
         <div className="mb-8 flex items-center justify-between">
            <div className="font-semibold sm:text-lg md:text-xl">
               Experience
            </div>
            <ArrowLink href="/Resume.pdf" left={false} text="Resume" newTab />
         </div>
         {experience.map((v, i) => (
            <div
               key={i}
               className="flex sm:mb-4 sm:flex-col-reverse sm:gap-1 md:mb-2 md:flex-row md:gap-0"
            >
               <div className="font-extralight opacity-50">{v.date}</div>
               <div className="sm:static md:absolute md:ml-36">
                  {v.position} <Link href={v.href}>@ {v.company}</Link>
               </div>
            </div>
         ))}
      </div>
   );
};

export default Experience;
