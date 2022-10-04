import "../styles/globals.css";
import type { AppProps } from "next/app";
import Helmet from "../components/Helmet";

function MyApp({ Component, pageProps }: AppProps) {
  return (
    <>
      <Component {...pageProps} />
      <Helmet />
    </>
  );
}

export default MyApp;
