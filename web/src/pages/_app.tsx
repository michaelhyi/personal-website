import type { AppProps } from "next/app";
import Client from "../components/Client";
import "../styles/globals.css";

export const metadata = {
  title: "Michael Yi",
  description: "© 2023 Michael Yi, All Rights Reserved.",
  icons: {
    icon: "/Michael.png",
  },
};

export default function App({ Component, pageProps }: AppProps) {
  return (
    <Client>
      <Component {...pageProps} />
    </Client>
  );
}
