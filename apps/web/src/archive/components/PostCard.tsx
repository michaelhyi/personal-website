"use client";

import { format } from "date-fns";
import Link from "next/link";
import { FC } from "react";

interface Props {
  id: number;
  title: string;
  description: string;
  date: Date;
}

const PostCard: FC<Props> = ({ id, title, description, date }) => {
  return (
    <Link
      className="flex cursor-pointer gap-4 duration-500 hover:opacity-50 sm:flex-col md:flex-row"
      href={`/blog/${id}`}
    >
      <div>
        <div className="font-semibold">{title}</div>
        <div className="mt-2 text-xs opacity-75">
          {format(new Date(date), "PPP")}
        </div>
        <div className="mt-2 text-xs opacity-75">{description}</div>
      </div>
    </Link>
  );
};

export default PostCard;
