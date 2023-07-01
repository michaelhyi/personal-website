import Client from "@/app/components/Client";
import "./globals.css";
import { font } from "./utils/font";

export const metadata = {
  title: "Michael Yi",
  description: "Copyright 2023 Michael Yi, All Rights Reserved.",
  icons: {
    icon: "/Michael.png",
  },
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="en">
      <body className={font.className}>
        <Client>{children}</Client>
      </body>
    </html>
  );
}
