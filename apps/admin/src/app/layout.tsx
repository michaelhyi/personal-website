import type { ReactNode } from "react";
import type { Metadata } from "next";
import { font } from "@personal-website/utils";
import Providers from "../components/Providers";
import "./globals.css";

export const metadata: Metadata = {
  title: "Admin - Michael Yi",
  description: "© 2023 Michael Yi, All Rights Reserved.",
  icons: {
    icon: "/michael.png",
  },
};

export default function RootLayout({ children }: { children: ReactNode }) {
  return (
    <html lang="en">
      <body className={font.className}>
        <Providers>{children}</Providers>
      </body>
    </html>
  );
}
