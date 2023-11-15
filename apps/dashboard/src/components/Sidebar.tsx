"use client";

import Image from "next/image";
import Link from "next/link";
import { FaPencilAlt } from "react-icons/fa";

const Sidebar = () => {
  return (
    <div className="fixed top-0 left-0 h-screen w-60 flex flex-col bg-neutral-700 text-white shadow-2xl">
      <div className="flex flex-col items-center mx-auto mt-12">
        <Image
          alt="michael"
          className="rounded-full"
          height={75}
          src="/michael.png"
          width={75}
        />
        <div className="mt-4 text-xl font-normal">Michael Yi</div>
      </div>
      <div className="mt-10" />
      <Link
        href="/dashboard/blog"
        className="flex items-center ml-8 gap-6 text-neutral-200 duration-500 hover:opacity-50"
      >
        <FaPencilAlt />
        <div className="text-sm font-normal">Blog</div>
      </Link>
    </div>
  );
};

export default Sidebar;
