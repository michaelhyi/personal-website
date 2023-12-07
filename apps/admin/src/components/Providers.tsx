"use client";

import { CacheProvider } from "@chakra-ui/next-js";
import { ChakraProvider } from "@chakra-ui/react";
import { useEffect, useState, type ReactNode } from "react";

export default function Providers({ children }: { children: ReactNode }) {
  const [mounted, setMounted] = useState(false);

  useEffect(() => {
    setMounted(true);
  }, []);

  if (!mounted) return;

  return (
    <CacheProvider>
      <ChakraProvider resetCSS={false}>{children}</ChakraProvider>
    </CacheProvider>
  );
}
