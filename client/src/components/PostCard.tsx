"use client";

import Link from "next/link";
import { format } from "date-fns";

interface Props {
  id: number;
  title: string;
  description: string;
  date: Date;
}

const PostCard: React.FC<Props> = ({ id, title, description, date }) => {
  return (
    <Link
      className="flex sm:flex-col md:flex-row gap-4 cursor-pointer duration-500 hover:opacity-50"
      href={`/blog/${id}`}
    >
      <div>
        <div className="font-semibold">{title}</div>
        <div className="text-xs mt-2 opacity-75">
          {format(new Date(date), "PPP")}
        </div>
        <div className="text-xs mt-2 opacity-75">{description}</div>
      </div>
    </Link>
  );
};

export default PostCard;
