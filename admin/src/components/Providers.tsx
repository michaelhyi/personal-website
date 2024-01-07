"use client";

import { Analytics } from "@vercel/analytics/react";
import { AnimatePresence } from "framer-motion";
import React, { type ReactNode, useEffect, useState } from "react";

export default function Providers({ children }: { children: ReactNode }) {
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
