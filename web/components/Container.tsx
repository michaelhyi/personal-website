"use client";

import { motion } from "framer-motion";
import { FC, ReactNode } from "react";
import Footer from "./Footer";

interface Props {
   children: ReactNode;
   absoluteFooter?: boolean;
}

const Container: FC<Props> = ({ children, absoluteFooter = false }) => {
   return (
      <motion.div
         initial={{ opacity: 0 }}
         animate={{ opacity: 1 }}
         transition={{ duration: 0.75 }}
         className="flex flex-col mx-auto pt-20 sm:w-[320px] md:w-[480px] lg:w-[512px] xl:w-[576px]"
      >
         {children}
         <Footer absolute={absoluteFooter} />
      </motion.div>
   );
};

export default Container;