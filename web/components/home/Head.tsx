"use client";

import Image from "next/image";
import Link from "next/link";

const About = () => {
   return (
      <div className="flex items-center gap-8 sm:flex-col sm:text-center  md:flex-row md:text-left">
         <Image
            src="/Michael.png"
            alt="michael"
            height={100}
            width={100}
            className="rounded-full"
         />
         <div>
            <div className="text-2xl font-normal">Michael Yi</div>
            <div className="mb-2 text-sm font-light text-neutral-500">
               Software Engineer
            </div>
            <Link
               href="https://michael-yi.com/Resume.pdf"
               className="cursor-pointer
                          rounded-2xl
                          bg-neutral-100 
                          px-5
                          py-1
                          text-center
                          text-xs
                          font-light 
                          text-neutral-500 
                          underline
                          duration-500
                          hover:opacity-50"
            >
               Resume
            </Link>
         </div>
      </div>
   );
};

export default About;
