import { format } from "date-fns";
import Image from "next/image";
import type { FC } from "react";
import { FiArrowUpRight } from "react-icons/fi";
import { readPostImageUrl } from "services";
import type { Post } from "types";

interface Props {
  data: Post;
}

const PostCard: FC<Props> = ({ data }) => {
  return (
    <div className="flex sm:flex-col md:flex-row gap-12">
      <Image
        className="rounded-lg border-[1px] border-neutral-300 shadow-md"
        src={readPostImageUrl(data.id.toString())}
        alt={data.title}
        width={200}
        height={120}
      />
      <div className="">
        <div className="text-[13px] font-light text-neutral-400 ">
          {format(new Date(data.date), "PPP")}
        </div>
        <div className="flex gap-1 text-sm font-normal cursor-pointer duration-500 hover:opacity-50">
          {data.title}
          <FiArrowUpRight size={12} />
        </div>
        <div className="mt-2 text-xs text-neutral-400 line-clamp-3">
          {data.body}
        </div>
      </div>
    </div>
  );
};

export default PostCard;
