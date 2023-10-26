"use client";

import Image from "next/image";
import Link from "next/link";

const About = () => {
   return (
      <div className="flex sm:flex-col sm:text-center md:text-left md:flex-row  items-center gap-8">
         <Image
            src="/Michael.png"
            alt="michael"
            height={100}
            width={100}
            className="rounded-full"
         />
         <div>
            <div className="font-normal text-2xl">Michael Yi</div>
            <div className="font-light text-sm text-neutral-500 mb-2">
               Software Engineer
            </div>
            <Link
               href="https://michael-yi.com/Resume.pdf"
               className="text-center
                          cursor-pointer
                          rounded-2xl 
                          duration-500
                          hover:opacity-50
                          bg-neutral-100
                          text-neutral-500
                          font-light 
                          text-xs 
                          py-1
                          px-5
                          underline"
            >
               Resume
            </Link>
         </div>
      </div>
   );
};

export default About;
