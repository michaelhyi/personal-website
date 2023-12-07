"use client";

import { CacheProvider } from "@chakra-ui/next-js";
import { ChakraProvider } from "@chakra-ui/react";
import { Analytics } from "@vercel/analytics/react";
import { AnimatePresence } from "framer-motion";
import type { ReactNode } from "react";
import { useEffect, useState } from "react";

export default function Providers({ children }: { children: ReactNode }) {
  const [mounted, setMounted] = useState(false);

  useEffect(() => {
    setMounted(true);
  }, []);

  if (!mounted) return;

  return (
    <CacheProvider>
      <ChakraProvider resetCSS={false}>
        <Analytics />
        <AnimatePresence>{children}</AnimatePresence>
      </ChakraProvider>
    </CacheProvider>
  );
}
