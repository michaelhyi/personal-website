import "../styles/globals.css";
import type { AppProps } from "next/app";
import Helmet from "../components/Helmet";
import { ChakraProvider } from "@chakra-ui/react";
import { Analytics } from "@vercel/analytics/react";

function MyApp({ Component, pageProps }: AppProps) {
  return (
    <ChakraProvider>
      <Component {...pageProps} />
      <Helmet />
      <Analytics />
    </ChakraProvider>
  );
}

export default MyApp;
