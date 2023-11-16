"use client";

import Image from "next/image";
import Link from "next/link";
import { FaHome, FaProjectDiagram } from "react-icons/fa";
import { MdOutlineWork } from "react-icons/md";
import { IoLogOutOutline } from "react-icons/io5";
import { TfiWrite } from "react-icons/tfi";
import { signOut } from "next-auth/react";
import { useCallback } from "react";

const Sidebar = () => {
  const handleLogout = useCallback(async () => {
    localStorage.removeItem("token");
    await signOut({ redirect: true, callbackUrl: "/" });
  }, []);

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
        <div className="mt-1 text-xs font-light text-neutral-400">
          Personal Website Dashboard
        </div>
      </div>
      <div className="px-6 py-8">
        <div className="text-neutral-400 text-xs font-semibold">MENU</div>
        <Link
          href="/dashboard"
          className="mt-4 flex items-center gap-5 text-neutral-200 duration-500 hover:opacity-50"
        >
          <FaHome size={12.5} />
          <div className="text-sm font-medium">Home</div>
        </Link>
        <Link
          href="/dashboard/experience"
          className="mt-3 flex items-center gap-5 text-neutral-200 duration-500 hover:opacity-50"
        >
          <MdOutlineWork size={12.5} />
          <div className="text-sm font-medium">Experience</div>
        </Link>
        <Link
          href="/dashboard/projects"
          className="mt-3 flex items-center gap-5 text-neutral-200 duration-500 hover:opacity-50"
        >
          <FaProjectDiagram size={12.5} />
          <div className="text-sm font-medium">Projects</div>
        </Link>
        <Link
          href="/dashboard/blog"
          className="mt-3 flex items-center gap-5 text-neutral-200 duration-500 hover:opacity-50"
        >
          <TfiWrite size={12.5} />
          <div className="text-sm font-medium">Blog</div>
        </Link>
        <div className="mt-12 text-neutral-400 text-xs font-semibold">USER</div>
        <button
          onClick={handleLogout}
          className="mt-4 flex items-center gap-5 text-neutral-200 duration-500 hover:opacity-50"
          type="button"
        >
          <IoLogOutOutline size={17.5} />
          <div className="text-sm font-medium">Logout</div>
        </button>
      </div>
    </div>
  );
};

export default Sidebar;
