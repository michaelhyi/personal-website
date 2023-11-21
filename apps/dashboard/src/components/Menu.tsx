"use client";

import type { FC } from "react";
import { IoPencil, IoTrashOutline } from "react-icons/io5";

interface Props {
  id: number;
  handleToggleModal: (title?: string, index?: number) => void;
}

const Menu: FC<Props> = ({ id, handleToggleModal }) => {
  return (
    <div className="z-10 flex flex-col gap-2 absolute rounded-md shadow-lg w-36 bg-neutral-700 text-neutral-200 font-semibold overflow-hidden right-1 top-10 p-3">
      <button
        onClick={() => {
          handleToggleModal("Edit Post", id);
        }}
        type="button"
        className="flex items-center gap-2 duration:500 hover:opacity-50"
      >
        <IoPencil /> Edit Post
      </button>
      <button
        onClick={() => {
          handleToggleModal("Delete Post", id);
        }}
        type="button"
        className="flex items-center gap-2 duration:500 hover:opacity-50"
      >
        <IoTrashOutline />
        Delete Post
      </button>
    </div>
  );
};

export default Menu;
