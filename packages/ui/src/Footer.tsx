"use client";

import Link from "next/link";
import React, { type FC } from "react";
import type { FooterLink } from "types";
import { footer } from "./data/footer";

interface Props {
  absolute?: boolean;
}

export const Footer: FC<Props> = ({ absolute = false }) => {
  return (
    <div
      className={`flex 
                     flex-col
                     items-center
                     gap-3
                     text-[10px]
                     text-neutral-300
                     ${
                       absolute
                         ? "absolute bottom-4 left-0 right-0"
                         : "mt-16 pb-4"
                     }`}
    >
      <div className="flex gap-2">
        {footer.map((v: FooterLink) => (
          <Link
            key={v.href}
            href={v.href}
            rel="noopener noreferrer"
            target="_blank"
            className="duration-500 hover:opacity-50"
          >
            <v.icon size={15} />
          </Link>
        ))}
      </div>
      <div>&copy; 2023 Michael Yi, All Rights Reserved.</div>
    </div>
  );
};
