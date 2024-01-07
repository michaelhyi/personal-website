"use client";

import { motion } from "framer-motion";
import React, { type ReactNode } from "react";
import Footer from "./Footer";

export default function Container({
  children,
  absoluteFooter = false,
}: {
  children: ReactNode;
  absoluteFooter?: boolean;
}) {
  return (
    <div className="bg-neutral-800 text-white min-h-screen">
      <motion.div
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ duration: 0.75 }}
        className="mx-auto flex flex-col pt-20 sm:w-[320px] md:w-[480px] lg:w-[512px] xl:w-1/2"
      >
        {children}
        <Footer absolute={absoluteFooter} />
      </motion.div>
    </div>
  );
}
