"use client";

import Link from "next/link";
import React from "react";
import { FaArrowLeftLong } from "react-icons/fa6";
import Hoverable from "./Hoverable";

export default function BackButton({
  href,
  text,
}: {
  href: string;
  text: string;
}) {
  return (
    <Hoverable>
      <Link
        href={href}
        className="flex items-center gap-2 text-xs text-neutral-300"
      >
        <FaArrowLeftLong /> Back to {text}
      </Link>
    </Hoverable>
  );
}
