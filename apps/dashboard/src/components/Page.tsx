"use client";

import { format } from "date-fns";
import Link from "next/link";
import type { FC } from "react";
import { FaPlus, FaPencilAlt } from "react-icons/fa";
import type { Experience, Post, Project } from "types";
import Container from "@/components/Container";

interface Props {
  title: string;
  data: Experience[] | Project[] | Post[];
}

const Page: FC<Props> = ({ title, data }) => {
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
              {data.map((v: Experience | Project | Post) => (
                <tr key={v.id}>
                  <td className="px-6 py-4">{v.id}</td>
                  <td className="px-6 py-4">{format(new Date(v.date), "P")}</td>
                  <td className="px-6 pr-96 py-4">
                    {"title" in v ? v.title : v.name}
                  </td>
                  <td className="px-3 py-4">
                    <Link
                      href={`/dashboard/${title.toLowerCase()}/edit/${v.id}`}
                      className="duration-500 hover:opacity-50"
                    >
                      <FaPencilAlt />
                    </Link>
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
