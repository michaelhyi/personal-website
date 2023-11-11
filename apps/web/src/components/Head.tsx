"use client";

import Image from "next/image";
import Link from "next/link";
import { head } from "@/data/head";

const Head = () => {
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
      <div className="mt-4 text-2xl font-light">Michael Yi</div>
      <div className="mt-1 text-xs font-light text-neutral-400">
        Software Engineer
      </div>
      <div className="flex mt-2 text-[10px] text-neutral-300">
        {head.map((v, i) => {
          return (
            <>
              {i !== 0 && <>&nbsp;&nbsp;&#183;&nbsp;&nbsp;</>}
              <Link
                href={v.href}
                className="cursor-pointer
                    duration-500
                    hover:opacity-50"
              >
                {v.name}
              </Link>
            </>
          );
        })}
      </div>
    </div>
  );
};

export default Head;
