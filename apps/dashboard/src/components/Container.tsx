"use client";

import type { FC, ReactNode } from "react";
import Sidebar from "./Sidebar";

interface Props {
  children: ReactNode;
}

const Container: FC<Props> = ({ children }) => {
  return (
    <div className="flex bg-neutral-800 text-white min-h-screen">
      <Sidebar />
      <div className="ml-60 px-36 py-24">{children}</div>
    </div>
  );
};

export default Container;
