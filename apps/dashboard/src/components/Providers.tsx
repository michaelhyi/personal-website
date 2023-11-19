"use client";

import { CacheProvider } from "@chakra-ui/next-js";
import { ChakraProvider } from "@chakra-ui/react";
import type { FC, ReactNode } from "react";
import { useEffect, useState } from "react";

interface Props {
  children: ReactNode;
}

const Providers: FC<Props> = ({ children }) => {
  const [mounted, setMounted] = useState(false);

  useEffect(() => {
    setMounted(true);
  }, []);

  if (!mounted) return;

  return (
    <CacheProvider>
      <ChakraProvider resetCSS={false}>{children} </ChakraProvider>
    </CacheProvider>
  );
};

export default Providers;
