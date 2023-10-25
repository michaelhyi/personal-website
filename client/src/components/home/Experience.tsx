"use client";

import { experience } from "@/data/experience";
import Section from "../Section";

const Experience = () => {
   return (
      <Section title="Experience">
         {experience.map((v, i) => (
            <div
               key={i}
               className={`
               flex 
               ${i !== experience.length - 1 && "sm:mb-4 md:mb-0"} 
               sm:flex-col-reverse 
               sm:gap-1 
               md:flex-row 
               md:gap-0 
               `}
            >
               <div className="font-light text-[13px] text-neutral-400 w-48">
                  {v.date}
               </div>
               <div className="w-96">
                  <div className="text-sm font-normal text-neutral-800">
                     {v.position} at {v.company}
                  </div>
                  <div className="text-neutral-400 text-xs mt-2">
                     {v.description}
                  </div>
               </div>
            </div>
         ))}
      </Section>
   );
};

export default Experience;
