"use client";

import { honors } from "../../data/honors";
import Section from "../Section";

const Honors = () => {
   return (
      <Section title="Honors">
         {honors.map((v, i) => (
            <div
               key={i}
               className={`
               flex 
               ${i !== honors.length - 1 && "sm:mb-4 md:mb-0"} 
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
                     {v.title}
                  </div>
               </div>
            </div>
         ))}
      </Section>
   );
};

export default Honors;
