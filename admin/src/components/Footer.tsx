"use client";

import Link from "next/link";
import React from "react";
import { AiFillGithub, AiFillLinkedin } from "react-icons/ai";
import { Hoverable } from ".";

export default function Footer({ absolute = false }: { absolute?: boolean }) {
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
        <Hoverable>
          <Link
            href="https://github.com/michaelhyi"
            rel="noopener noreferrer"
            target="_blank"
          >
            <AiFillGithub size={15} />
          </Link>
        </Hoverable>
        <Hoverable>
          <Link
            href="https://www.linkedin.com/in/michaelhyi/"
            rel="noopener noreferrer"
            target="_blank"
          >
            <AiFillLinkedin size={15} />
          </Link>
        </Hoverable>
      </div>
      <div>&copy; 2023 Michael Yi, All Rights Reserved.</div>
    </div>
  );
}
