"use client";

import Image from "next/image";
import { icons } from "@/data/icons";
import Link from "../links/Link";

const About = () => {
   return (
      <div>
         <div className="mt-36 flex gap-8 sm:flex-col md:flex-row">
            <Image
               src="/Michael.png"
               alt="me"
               height={115}
               width={115}
               className="rounded-full"
            />
            <div>
               <div className="text-3xl font-bold">Michael Yi</div>
               <div className="mt-2">Software Engineer</div>
               <div>
                  CS <Link href="https://www.gatech.edu/">@ Georgia Tech</Link>
               </div>
               <div className="mt-2 flex gap-2">
                  {icons.map(({ icon: Icon, href }, i) => (
                     <Link key={i} href={href}>
                        <Icon size={20} className="text-pink-300" />
                     </Link>
                  ))}
               </div>
            </div>
         </div>
         <div className="mt-8">
            I love building. With my aptitude for leadership, communication, and
            passion, I deliver exceptional results in software and
            entrepreneurship.
         </div>
         {/* <div className="mt-6">
        Check out my&nbsp;
        <NextLink
          className="text-pink-300 cursor-pointer duration-500 hover:opacity-50 font-bold underline"
          href="/blog"
        >
          blog
        </NextLink>
        &nbsp;where I write about my passion for music and cinema!
      </div> */}
      </div>
   );
};

export default About;
