"use client";

import type { ReactNode } from "react";

export default function Hoverable({
  children,
}: Readonly<{
  children: ReactNode;
}>) {
  return (
    <div className="cursor-pointer duration-300 hover:opacity-50">
      {children}
    </div>
  );
}
