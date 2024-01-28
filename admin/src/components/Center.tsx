"use client";

import type { ReactNode } from "react";

export default function Center({
  children,
}: Readonly<{ children: ReactNode }>) {
  return (
    <div className="absolute left-1/2 top-1/2 -translate-x-1/2 -translate-y-1/2 transform">
      {children}
    </div>
  );
}
