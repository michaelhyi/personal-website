"use client";

import { format } from "date-fns";
import type { FC } from "react";
import { IoEllipsisHorizontal } from "react-icons/io5";
import type { Post } from "types";
import Menu from "./Menu";

interface Props {
  data: Post[];
  menuOpen: boolean[];
  handleToggleMenu: (index: number) => void;
  handleToggleModal: (title?: string, index?: number) => void;
}

const TableBody: FC<Props> = ({
  data,
  menuOpen,
  handleToggleMenu,
  handleToggleModal,
}) => {
  return (
    <tbody>
      {data.map((v: Post, i: number) => (
        <tr key={v.id}>
          <td className="px-6 py-4">{v.id}</td>
          <td className="px-6 py-4">{format(new Date(v.date), "P")}</td>
          <td className="px-6 pr-96 py-4">{v.title}</td>
          <td className="relative px-3 py-4">
            <button
              onClick={() => {
                handleToggleMenu(i);
              }}
              type="button"
              className="duration-500 hover:opacity-50"
            >
              <IoEllipsisHorizontal />
            </button>
            {menuOpen[i] ? (
              <Menu id={v.id} handleToggleModal={handleToggleModal} />
            ) : null}
          </td>
        </tr>
      ))}
    </tbody>
  );
};

export default TableBody;
