import { format } from "date-fns";
import Image from "next/image";
import { FiArrowUpRight } from "react-icons/fi";
import { readPostImageUrl } from "@personal-website/services";
import type { Post } from "@personal-website/types";
import { IoEllipsisHorizontal } from "react-icons/io5";
import Menu from "./Menu";

export default function PostCard({
  data,
  index,
  menuOpen,
  handleToggleMenu,
  handleToggleModal,
}: {
  data: Post;
  index: number;
  menuOpen: boolean;
  handleToggleMenu: (index: number) => void;
  handleToggleModal: (id?: number | undefined) => void;
}) {
  return (
    <div className="flex sm:flex-col md:flex-row gap-12">
      <Image
        className="rounded-lg border-[1px] border-neutral-300 shadow-md"
        src={readPostImageUrl(data.id)}
        alt={data.title}
        width={200}
        height={120}
      />
      <div className="w-full">
        <div className="text-[13px] font-light text-neutral-400 ">
          {format(new Date(data.date), "PPP")}
        </div>
        <div className="flex items-center justify-between">
          <a
            href={`${process.env.NEXT_PUBLIC_WEB_URL}/blog/${data.title}`}
            target="_blank"
            rel="noopener noreferrer"
            className="flex gap-1 text-sm font-normal cursor-pointer duration-500 hover:opacity-50"
          >
            {data.title}
            <FiArrowUpRight size={12} />
          </a>
          <div className="relative">
            <IoEllipsisHorizontal
              className="cursor-pointer duration-500 hover:opacity-50"
              onClick={() => {
                handleToggleMenu(index);
              }}
            />
            {menuOpen ? (
              <Menu id={data.id} handleToggleModal={handleToggleModal} />
            ) : null}
          </div>
        </div>
        <div className="mt-2 text-xs text-neutral-400 line-clamp-3">
          {
            // eslint-disable-next-line prefer-named-capture-group -- unneccessary regex errors
            data.content.replace(/(<([^>]+)>)/gi, "")
          }
        </div>
      </div>
    </div>
  );
}
