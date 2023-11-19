"use client";

import { format } from "date-fns";
import Link from "next/link";
import { useState, type FC, type ReactNode, useCallback } from "react";
import { FaPlus } from "react-icons/fa";
import {
  IoEllipsisHorizontal,
  IoPencil,
  IoTrashOutline,
} from "react-icons/io5";
import type { Experience, Post, Project } from "types";
import Container from "@/components/Container";

interface Props {
  title: string;
  data: Experience[] | Project[] | Post[];
}

const Page: FC<Props> = ({ title, data }) => {
  const [menuOpen, setMenuOpen] = useState<boolean[]>(
    new Array(data.length).fill(false),
  );

  const toggleMenu = useCallback(
    (index: number) => {
      setMenuOpen(
        menuOpen.map((v, i) => {
          if (i === index) return !v;
          return v;
        }),
      );
    },
    [menuOpen],
  );

  return (
    <Container>
      <div className="flex flex-col">
        <div className="font-bold text-3xl">{title}</div>
        <div className="mt-12 border-[1px] rounded-md border-neutral-500 text-sm mx-auto bg-neutral-800">
          <table className="text-left">
            <thead className="border-b-[1px]  border-neutral-500">
              <tr>
                <th className="px-6 py-4">ID</th>
                <th className="px-6 py-4">Date</th>
                <th className="px-6 pr-96 py-4">
                  {title === "Blog" ? "Title" : "Name"}
                </th>
                <th className="px-3 py-4">
                  <Link
                    href={`/dashboard/${title.toLowerCase()}/create`}
                    className="duration-500 hover:opacity-50"
                  >
                    <FaPlus />
                  </Link>
                </th>
              </tr>
            </thead>
            <tbody>
              {data.map((v: Experience | Project | Post, i: number) => (
                <tr key={v.id}>
                  <td className="px-6 py-4">{v.id}</td>
                  <td className="px-6 py-4">
                    {title === "Blog"
                      ? format(new Date(v.date), "P")
                      : (v.date as ReactNode)}
                  </td>
                  <td className="px-6 pr-96 py-4">
                    {"title" in v ? v.title : v.name}
                  </td>
                  <td className="relative px-3 py-4">
                    <button
                      onClick={() => {
                        toggleMenu(i);
                      }}
                      type="button"
                      className="duration-500 hover:opacity-50"
                    >
                      <IoEllipsisHorizontal />
                    </button>
                    {menuOpen[i] && (
                      <div className="flex flex-col gap-2 absolute rounded-md shadow-lg w-36 bg-white text-neutral-700 font-semibold overflow-hidden right-0 top-10 p-3">
                        <div className="flex items-center gap-2">
                          <IoPencil /> Edit Post
                        </div>
                        <div className="flex items-center gap-2">
                          <IoTrashOutline />
                          Delete Post
                        </div>
                      </div>
                    )}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </Container>
  );
};

export default Page;
