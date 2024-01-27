"use client";

import { motion } from "framer-motion";
import type { ReactNode } from "react";
import Footer from "./Footer";

export default function Container({
  children,
  absoluteFooter = false,
}: Readonly<{
  children: ReactNode;
  absoluteFooter?: boolean;
}>) {
  return (
    <div className="bg-black text-white min-h-screen">
      <motion.div
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ duration: 0.75 }}
        className="flex flex-col pt-20 mx-auto sm:w-[320px] md:w-[480px] lg:w-[512px] xl:w-[768px]"
      >
        {children}
        <Footer absolute={absoluteFooter} />
      </motion.div>
    </div>
  );
}
