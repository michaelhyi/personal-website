"use client";

import { readPostImageUrl } from "@/services/post";
import type { Post } from "@/types/post";
import { format } from "date-fns";
import Image from "next/image";
import Link from "next/link";
import React from "react";
import Hoverable from "@/components/Hoverable";

export default function PostCard({ data }: { data: Post }) {
  return (
    <div className="relative">
      <Hoverable>
        <Link
          className="flex sm:flex-col md:flex-row gap-12"
          href={`${process.env.NEXT_PUBLIC_WEB_URL}/blog/${data.id}`}
        >
          <Image
            className="rounded-lg shadow-md"
            src={readPostImageUrl(data.id)}
            alt={data.title}
            width={200}
            height={200}
          />
          <div className="w-full">
            <div className="text-xs font-light text-neutral-500">
              {format(new Date(data.date), "PPP")}
            </div>
            <div className="mt-2 font-bold">{data.title}</div>
            <div className="mt-2 text-xs text-neutral-400 line-clamp-3">
              {data.content.replace(/(<([^>]+)>)/gi, "")}
            </div>
          </div>
        </Link>
      </Hoverable>
    </div>
  );
}
