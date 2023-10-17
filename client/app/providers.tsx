"use client";

import { CacheProvider } from "@chakra-ui/next-js";
import { ChakraProvider } from "@chakra-ui/react";
import { Analytics } from "@vercel/analytics/react";
import { ReactNode, useEffect, useState } from "react";

interface Props {
  children: ReactNode;
}

const Providers: React.FC<Props> = ({ children }) => {
  const [mounted, setMounted] = useState(false);

  useEffect(() => {
    setMounted(true);
  }, []);

  if (!mounted) return <></>;

  return (
    <CacheProvider>
      <ChakraProvider resetCSS={false}>
        <Analytics />
        {children}
      </ChakraProvider>
    </CacheProvider>
  );
};

export default Providers;
