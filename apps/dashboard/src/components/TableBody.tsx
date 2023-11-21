"use client";

import { format } from "date-fns";
import type { FC, ReactNode } from "react";
import { IoEllipsisHorizontal } from "react-icons/io5";
import type { Experience, Post, Project } from "types";
import Menu from "./Menu";

interface Props {
  title: string;
  data: Experience[] | Project[] | Post[];
  menuOpen: boolean[];
  handleToggleMenu: (index: number) => void;
  handleToggleModal: (title?: string, index?: number) => void;
}

const TableBody: FC<Props> = ({
  title,
  data,
  menuOpen,
  handleToggleMenu,
  handleToggleModal,
}) => {
  return (
    <tbody>
      {data.map((v: Experience | Project | Post, i: number) => (
        <tr key={v.id}>
          <td className="px-6 py-4">{v.id}</td>
          <td className="px-6 py-4">
            {title === "Blog"
              ? format(new Date(v.date), "P")
              : (v.date as ReactNode)}
          </td>
          <td className="px-6 pr-96 py-4">{"title" in v ? v.title : v.name}</td>
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
