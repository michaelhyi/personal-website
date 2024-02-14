"use client";

import Link from "next/link";
import { IoPencil, IoTrashOutline } from "react-icons/io5";
import Hoverable from "./Hoverable";

export default function Menu({
  id,
  handleToggleModal,
}: Readonly<{
  id: string;
  handleToggleModal: (id?: string | undefined) => void;
}>) {
  return (
    <div
      className="z-[1000]
                    flex 
                    flex-col 
                    gap-2 
                    absolute 
                    rounded-md 
                    shadow-lg 
                    w-36 
                    bg-neutral-800
                    text-white 
                    font-medium 
                    overflow-hidden 
                    right-0 
                    top-5 
                    p-3"
    >
      <Hoverable>
        <Link
          href={`/blog?mode=edit&id=${id}`}
          className="flex items-center gap-2 text-sm"
        >
          <IoPencil /> Edit Post
        </Link>
      </Hoverable>
      <Hoverable>
        <button
          onClick={() => {
            handleToggleModal(id);
          }}
          type="button"
          className="flex items-center gap-2 text-sm"
        >
          <IoTrashOutline />
          Delete Post
        </button>
      </Hoverable>
    </div>
  );
}
