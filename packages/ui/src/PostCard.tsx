"use client";

import { readPostImageUrl } from "@personal-website/services";
import type { Post } from "@personal-website/types";
import { format } from "date-fns";
import Image from "next/image";
import Link from "next/link";
import React from "react";
import { IoEllipsisHorizontal } from "react-icons/io5";
import Menu from "./Menu";
import Hoverable from "./Hoverable";

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
    <Hoverable>
      <Link
        className="flex sm:flex-col md:flex-row gap-12"
        href={`${process.env.NEXT_PUBLIC_WEB_URL}/blog/${data.title}`}
      >
        <Image
          className="rounded-lg border-[1px] border-neutral-300 shadow-md"
          src={readPostImageUrl(data.id)}
          alt={data.title}
          width={175}
          height={175}
        />
        <div className="w-full">
          <div className="flex items-center justify-between">
            <div className="text-xs font-light text-neutral-500">
              {format(new Date(data.date), "PPP")}
            </div>
            {admin && handleToggleMenu && handleToggleModal ? (
              <div className="relative">
                <IoEllipsisHorizontal
                  className="cursor-pointer duration-500 hover:opacity-50"
                  onClick={() => {
                    handleToggleMenu(index);
                  }}
                />
                {menuOpen ? (
                  <Menu id={data.id} handleToggleModal={handleToggleModal} />
                ) : null}
              </div>
            ) : null}
          </div>
          <a className="font-bold cursor-pointer duration-500 hover:opacity-50">
            {data.title}
          </a>
          <div className="mt-2 text-xs text-neutral-400 line-clamp-3">
            {data.content.replace(/(<([^>]+)>)/gi, "")}
          </div>
        </div>
      </Link>
    </Hoverable>
  );
}
