import "./globals.css";
import localFont from "next/font/local";

const font = localFont({
  src: [
    {
      path: "../public/fonts/SF-Pro-Text-Regular.otf",
      weight: "400",
      style: "normal",
    },
    {
      path: "../public/fonts/SF-Pro-Text-RegularItalic.otf",
      weight: "400",
      style: "italic",
    },
    {
      path: "../public/fonts/SF-Pro-Text-Medium.otf",
      weight: "500",
      style: "medium",
    },
    {
      path: "../public/fonts/SF-Pro-Text-MediumItalic.otf",
      weight: "500",
      style: "italic",
    },
    {
      path: "../public/fonts/SF-Pro-Text-Semibold.otf",
      weight: "600",
      style: "semibold",
    },
    {
      path: "../public/fonts/SF-Pro-Text-SemiboldItalic.otf",
      weight: "600",
      style: "italic",
    },
    {
      path: "../public/fonts/SF-Pro-Text-Bold.otf",
      weight: "700",
      style: "bold",
    },
    {
      path: "../public/fonts/SF-Pro-Text-BoldItalic.otf",
      weight: "700",
      style: "italic",
    },
  ],
});

export const metadata = {
  title: "Michael Yi",
  description: "Copyright 2023 Michael Yi. All Rights Reserved.",
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="en">
      <body className={font.className}>{children}</body>
    </html>
  );
}
