"use client";

import { icons } from "@/data/icons";
import Link from "next/link";
import { FC } from "react";

interface Props {
   absolute?: boolean;
}

const Footer: FC<Props> = ({ absolute = false }) => {
   return (
      <div
         className={`flex flex-col ${
            absolute ? "absolute bottom-4 left-0 right-0" : "mt-12 pb-4"
         } items-center gap-3 text-[10px] text-neutral-400`}
      >
         <div className="flex gap-2">
            {icons.map(({ icon: Icon, href }) => (
               <Link
                  key={href}
                  href={href}
                  rel="noopener noreferrer"
                  target="_blank"
                  className="duration-500 hover:opacity-50"
               >
                  <Icon size={15} />
               </Link>
            ))}
         </div>

         <div>&copy; 2023 Michael Yi, All Rights Reserved.</div>
      </div>
   );
};

export default Footer;
