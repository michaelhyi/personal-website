"use client";

import type { ReactNode } from "react";

export default function Hoverable({
  children,
  className,
}: Readonly<{
  children: ReactNode;
  className?: string;
}>) {
  return (
    <div
      className={`${className} cursor-pointer duration-500 hover:opacity-50`}
    >
      {children}
    </div>
  );
}
