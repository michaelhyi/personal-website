"use client";

import Image from "next/image";

const About = () => {
   return (
      <div className="flex sm:flex-col sm:text-center md:text-left md:flex-row gap-8 items-center">
         <Image
            src="/Michael.png"
            alt="michael"
            height={100}
            width={100}
            className="rounded-full"
         />
         <div>
            <div className="font-normal text-2xl">Michael Yi</div>
            <div className="font-light text-sm text-neutral-500">
               Software Engineer
            </div>
            <div className="text-neutral-500 font-light text-[10px] mt-1">
               Atlanta, GA
            </div>
         </div>
      </div>
   );
};

export default About;
