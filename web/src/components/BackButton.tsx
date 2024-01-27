"use client";

import Link from "next/link";
import { FaArrowLeftLong } from "react-icons/fa6";
import Hoverable from "./Hoverable";

export default function BackButton({
  href,
  text,
}: Readonly<{
  href: string;
  text: string;
}>) {
  return (
    <Hoverable>
      <Link
        href={href}
        className="flex items-center gap-2 text-xs text-neutral-400"
      >
        <FaArrowLeftLong /> Back to {text}
      </Link>
    </Hoverable>
  );
}
