import Client from "@/app/components/Client";
import { ReactNode } from "react";
import "./globals.css";
import { font } from "./utils/font";

interface Props {
  children: ReactNode;
}

export const metadata = {
  title: "Michael Yi",
  description: "Copyright 2023 Michael Yi, All Rights Reserved.",
  icons: {
    icon: "/Michael.png",
  },
};

const RootLayout: React.FC<Props> = ({ children }) => {
  return (
    <html lang="en">
      <body className={font.className}>
        <Client>{children}</Client>
      </body>
    </html>
  );
};

export default RootLayout;
