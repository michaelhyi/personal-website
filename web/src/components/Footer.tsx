"use client";

import Link from "next/link";
import { AiFillGithub, AiFillLinkedin } from "react-icons/ai";
import Hoverable from "./Hoverable";

const links = [
  {
    href: "https://github.com/michaelhyi/",
    icon: AiFillGithub,
  },
  {
    href: "https://www.linkedin.com/in/michaelhyi/",
    icon: AiFillLinkedin,
  },
];

export default function Footer({
  absolute = false,
}: Readonly<{ absolute?: boolean }>) {
  return (
    <div
      className={`flex 
                  flex-col
                  items-center
                  gap-3
                  text-[10px]
                  ${
                    absolute ? "absolute bottom-4 left-0 right-0" : "mt-16 pb-4"
                  }
                `}
    >
      <div className="flex gap-2">
        {links.map(({ href, icon: Icon }) => (
          <Hoverable key={href}>
            <Link href={href} rel="noopener noreferrer" target="_blank">
              <Icon size={15} />
            </Link>
          </Hoverable>
        ))}
      </div>
      <div>&copy; 2023 Michael Yi, All Rights Reserved.</div>
    </div>
  );
}
