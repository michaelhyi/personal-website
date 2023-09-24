import { ChakraProvider } from "@chakra-ui/react";
import { Analytics } from "@vercel/analytics/react";
import { Inter } from "next/font/google";
import { ReactNode, useEffect, useState } from "react";
import Metadata from "../metadata";

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

  return (
    <ChakraProvider resetCSS={false}>
      <Metadata />
      <Analytics />

      <main className={font.className}>{children}</main>
    </ChakraProvider>
  );
};

export default Providers;
