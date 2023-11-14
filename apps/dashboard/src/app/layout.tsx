import type { FC, ReactNode } from "react";
import type { Metadata } from "next";
import Providers from "@/components/Providers";
import { font } from "@/utils/font";
import "./globals.css";

interface Props {
  children: ReactNode;
}

export const metadata: Metadata = {
  title: "Dashboard - Michael Yi",
  description: "© 2023 Michael Yi, All Rights Reserved.",
  icons: {
    icon: "/michael.png",
  },
};

const RootLayout: FC<Props> = ({ children }) => {
  return (
    <html lang="en">
      <body className={font.className}>
        <Providers>{children}</Providers>
      </body>
    </html>
  );
};

export default RootLayout;
