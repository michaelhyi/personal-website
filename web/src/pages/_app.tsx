import { Analytics } from "@vercel/analytics/react";
import type { AppProps } from "next/app";
import Metadata from "../metadata";
import Providers from "../providers";
import "../styles/globals.css";

const App: React.FC<AppProps> = ({ Component, pageProps }) => {
  return (
    <Providers>
      <Metadata />
      <Component {...pageProps} />
      <Analytics />
    </Providers>
  );
};

export default App;
