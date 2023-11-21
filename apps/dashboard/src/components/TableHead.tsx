"use client";

import type { FC } from "react";
import { FaPlus } from "react-icons/fa";

interface Props {
  title: string;
  handleToggleModal: (title?: string, index?: number) => void;
}

const TableHead: FC<Props> = ({ title, handleToggleModal }) => {
  return (
    <thead className="border-b-[1px]  border-neutral-500">
      <tr>
        <th className="px-6 py-4">ID</th>
        <th className="px-6 py-4">Date</th>
        <th className="px-6 pr-96 py-4">
          {title === "Blog" ? "Title" : "Name"}
        </th>
        <th className="px-3 py-4">
          <button
            type="button"
            className="duration-500 hover:opacity-50"
            onClick={() => {
              handleToggleModal("Create Post");
            }}
          >
            <FaPlus />
          </button>
        </th>
      </tr>
    </thead>
  );
};

export default TableHead;
