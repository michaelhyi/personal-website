"use client";

import { readPostImageUrl } from "@/services/post";
import type { Post } from "@/types/post";
import { format } from "date-fns";
import Image from "next/image";
import Link from "next/link";
import React from "react";
import { IoEllipsisHorizontal } from "react-icons/io5";
import Hoverable from "./Hoverable";
import Menu from "./Menu";

export default function PostCard({
  admin = false,
  data,
  index,
  menuOpen,
  handleToggleMenu,
  handleToggleModal,
}: {
  admin?: boolean;
  data: Post;
  index: number;
  menuOpen?: boolean | undefined;
  handleToggleMenu?: (index: number) => void | undefined;
  handleToggleModal?: (id?: number | undefined) => void | undefined;
}) {
  return (
    <div className="relative">
      <Hoverable>
        <Link
          className="flex sm:flex-col md:flex-row gap-12"
          href={`${process.env.NEXT_PUBLIC_WEB_URL}/blog/${data.title}`}
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
      {admin && handleToggleMenu && handleToggleModal ? (
        <div className="z-50 absolute top-0 right-0">
          <Hoverable className="relative">
            <IoEllipsisHorizontal
              onClick={() => {
                handleToggleMenu(index);
              }}
            />
          </Hoverable>
          {menuOpen ? (
            <Menu id={data.id} handleToggleModal={handleToggleModal} />
          ) : null}
        </div>
      ) : null}
    </div>
  );
}
