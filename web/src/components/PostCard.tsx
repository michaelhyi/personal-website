"use client";

import type { Post } from "@/types/post";
import { format } from "date-fns";
import Link from "next/link";
import React from "react";
import { FiArrowUpRight } from "react-icons/fi";
import Hoverable from "@/components/Hoverable";

export default function PostCard({ data }: Readonly<{ data: Post }>) {
  return (
    <Hoverable>
      <Link
        className="flex sm:flex-col md:flex-row"
        href={`${process.env.NEXT_PUBLIC_WEB_URL}/blog/${data.id}`}
      >
        <div className="w-72 text-[13px] font-light text-neutral-400">
          {format(new Date(data.date), "PPP")}
        </div>
        <div className="flex w-full text-sm font-medium">
          {data.title} <FiArrowUpRight />
        </div>
      </Link>
    </Hoverable>
  );
}
