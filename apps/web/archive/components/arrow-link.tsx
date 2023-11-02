"use client";

import Link from "next/link";
import { FC } from "react";
import { AiOutlineArrowLeft, AiOutlineArrowRight } from "react-icons/ai";

interface Props {
  href: string;
  left: boolean;
  text: string;
  newTab?: boolean;
}

const ArrowLink: FC<Props> = ({ href, left, text, newTab = false }) => {
  return (
    <Link
      href={href}
      className="flex cursor-pointer items-center gap-2 font-bold text-pink-300 duration-500 hover:opacity-50"
      rel={newTab ? "noopener noreferrer" : ""}
      target={newTab ? "_blank" : ""}
    >
      {left && <AiOutlineArrowLeft />} {text} {!left && <AiOutlineArrowRight />}
    </Link>
  );
};

export default ArrowLink;
