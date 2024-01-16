"use client";

import Link from "next/link";
import React from "react";
import { IoPencil, IoTrashOutline } from "react-icons/io5";

export default function Menu({
  id,
  handleToggleModal,
}: Readonly<{
  id: string;
  handleToggleModal: (id?: string | undefined) => void;
}>) {
  return (
    <div className="z-[1000] flex flex-col gap-2 absolute rounded-md shadow-lg w-36 bg-black text-white font-medium overflow-hidden right-0 top-5 p-3 border-[1px]">
      <Link
        href={`/blog?mode=edit&id=${id}`}
        className="flex items-center gap-2 duration:500 hover:opacity-50 text-sm"
      >
        <IoPencil /> Edit Post
      </Link>
      <button
        onClick={() => {
          handleToggleModal(id);
        }}
        type="button"
        className="flex items-center gap-2 duration:500 hover:opacity-50 text-sm"
      >
        <IoTrashOutline />
        Delete Post
      </button>
    </div>
  );
}
