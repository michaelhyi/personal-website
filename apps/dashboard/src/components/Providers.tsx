"use client";

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

  return <>{children}</>;
};

export default Providers;
