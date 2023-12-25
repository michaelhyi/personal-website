"use client";

import React, { type ReactNode } from "react";

export default function Hoverable({
  className,
  children,
}: {
  className?: string;
  children: ReactNode;
}) {
  return (
    <div
      className={`${className} cursor-pointer duration-500 hover:opacity-50`}
    >
      {children}
    </div>
  );
}
