"use client";

import Image from "next/image";
import Link from "next/link";
import { hero } from "@/data/hero";
import Hoverable from "./Hoverable";

export default function Hero() {
  return (
    <div
      className="absolute
             left-1/2
             top-1/2
             -translate-x-1/2
             -translate-y-1/2
             transform
             flex
             flex-col
             items-center
             text-center"
    >
      <Image
        alt="michael"
        className="rounded-full"
        height={100}
        src="/michael.png"
        width={100}
      />
      <div className="mt-4 text-2xl font-medium">Michael Yi</div>
      <div className="mt-1 text-xs font-light opacity-50">
        Software Engineer
      </div>
      <div className="flex mt-2 text-[10px]">
        {hero.map((v, i) => {
          return (
            <Hoverable>
              {i !== 0 && <>&nbsp;&nbsp;&#183;&nbsp;&nbsp;</>}
              <Link href={v.href}>{v.name}</Link>
            </Hoverable>
          );
        })}
      </div>
    </div>
  );
}
