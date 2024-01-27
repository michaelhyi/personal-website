"use client";

import Image from "next/image";
import Link from "next/link";
import Center from "./Center";
import Hoverable from "./Hoverable";

const hero = [
  {
    name: "About",
    href: "/about",
  },
  {
    name: "Portfolio",
    href: "/portfolio",
  },
  {
    name: "Blog",
    href: "/blog",
  },
  {
    name: "Resume",
    href: `${process.env.NEXT_PUBLIC_WEB_URL}/Resume.pdf`,
  },
];

export default function Hero() {
  return (
    <Center>
      <div className="flex flex-col items-center">
        <Image
          src="/michael.png"
          alt="michael"
          className="rounded-full"
          height={100}
          width={100}
        />
        <div className="mt-4 text-2xl font-medium">Michael Yi</div>
        <div className="mt-1 text-xs font-light text-neutral-500">
          Software Engineer
        </div>
        <div className="flex mt-2 text-[10px]">
          {hero.map((v, i) => {
            return (
              <Hoverable key={v.name}>
                {i !== 0 && <>&nbsp;&nbsp;&#183;&nbsp;&nbsp;</>}
                <Link href={v.href}>{v.name}</Link>
              </Hoverable>
            );
          })}
        </div>
      </div>
    </Center>
  );
}
