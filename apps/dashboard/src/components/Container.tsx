"use client";

import type { FC, ReactNode } from "react";

interface Props {
  children: ReactNode;
}

const Container: FC<Props> = ({ children }) => {
  return (
    <div className="bg-neutral-800 text-white min-h-screen">{children} </div>
  );
};

export default Container;
