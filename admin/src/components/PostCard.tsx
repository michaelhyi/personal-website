"use client";

import { format } from "date-fns";
import Link from "next/link";
import React from "react";
import { IoEllipsisHorizontal } from "react-icons/io5";
import Hoverable from "./Hoverable";
import Menu from "./Menu";
import type { Post } from "@/types/post";

export default function PostCard({
  admin = false,
  data,
  index,
  menuOpen,
  handleToggleMenu,
  handleToggleModal,
}: Readonly<{
  admin?: boolean;
  data: Post;
  index: number;
  menuOpen: boolean;
  handleToggleMenu: (index: number) => void;
  handleToggleModal: (id?: string | undefined) => void;
}>) {
  return (
    <div className="relative">
      <Hoverable>
        <Link
          className="flex sm:flex-col md:flex-row"
          href={`${process.env.NEXT_PUBLIC_WEB_URL}/blog/${data.id}`}
        >
          <div className="w-48 text-[13px] font-light text-neutral-400">
            {format(new Date(data.date), "PPP")}
          </div>
          <div className="text-sm font-semibold">{data.title}</div>
        </Link>
      </Hoverable>
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
    </div>
  );
}
