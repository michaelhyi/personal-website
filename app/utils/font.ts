import localFont from "next/font/local";

export const font = localFont({
  src: [
    {
      path: "../../public/fonts/SF-Pro-Text-Regular.otf",
      weight: "400",
      style: "normal",
    },
    {
      path: "../../public/fonts/SF-Pro-Text-RegularItalic.otf",
      weight: "400",
      style: "italic",
    },
    {
      path: "../../public/fonts/SF-Pro-Text-Bold.otf",
      weight: "700",
      style: "bold",
    },
    {
      path: "../../public/fonts/SF-Pro-Text-BoldItalic.otf",
      weight: "700",
      style: "italic",
    },
  ],
});
