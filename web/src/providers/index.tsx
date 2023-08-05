import localFont from "next/font/local";
import { ReactNode, useEffect, useState } from "react";

interface Props {
  children: ReactNode;
}

const font = localFont({
  src: [
    {
      path: "../../public/fonts/SF-Pro-Text-Regular.otf",
      weight: "400",
      style: "normal",
    },
    {
      path: "../../public/fonts/SF-Pro-Text-Bold.otf",
      weight: "700",
      style: "bold",
    },
  ],
});

const Providers: React.FC<Props> = ({ children }) => {
  const [mounted, setMounted] = useState(false);

  useEffect(() => {
    setMounted(true);
  }, []);

  if (!mounted) return <></>;

  return <main className={font.className}>{children}</main>;
};

export default Providers;
