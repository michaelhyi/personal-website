import { Inter } from "next/font/google";
import { ReactNode, useEffect, useState } from "react";

interface Props {
  children: ReactNode;
}

const font = Inter({ subsets: ["latin"] });

const Providers: React.FC<Props> = ({ children }) => {
  const [mounted, setMounted] = useState(false);

  useEffect(() => {
    setMounted(true);
  }, []);

  if (!mounted) return <></>;

  return <main className={font.className}>{children}</main>;
};

export default Providers;
