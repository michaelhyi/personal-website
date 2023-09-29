import type { AppProps } from "next/app";
import Providers from "../providers";
import "../styles/globals.css";

const App: React.FC<AppProps> = ({ Component, pageProps }) => {
  return (
    <Providers>
      <Component {...pageProps} />
    </Providers>
  );
};

export default App;
