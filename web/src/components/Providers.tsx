"use client";

import { Analytics } from "@vercel/analytics/react";
import { AnimatePresence } from "framer-motion";
import { useEffect, useState, type ReactNode } from "react";

export default function Providers({
  children,
}: Readonly<{ children: ReactNode }>) {
  const [mounted, setMounted] = useState(false);

  useEffect(() => {
    setMounted(true);
  }, []);

  if (!mounted) return;

  return (
    <>
      <Analytics />
      <AnimatePresence>{children}</AnimatePresence>
    </>
  );
}
