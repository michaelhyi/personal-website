"use client";

import Link from "next/link";
import type { FC } from "react";
import { contact } from "@/data/contact";

interface Props {
  absolute?: boolean;
}

const Footer: FC<Props> = ({ absolute = false }) => {
  return (
    <div
      className={`flex 
                     flex-col
                     items-center
                     gap-3
                     text-[10px]
                     text-neutral-400
                     ${
                       absolute
                         ? "absolute bottom-4 left-0 right-0"
                         : "mt-16 pb-4"
                     }`}
    >
      <div className="flex gap-2">
        {contact.map(({ icon: Icon, href }) => (
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
