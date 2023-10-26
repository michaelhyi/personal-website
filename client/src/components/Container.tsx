"use client";

import { FC, ReactNode } from "react";
import Footer from "./Footer";

interface Props {
   children: ReactNode;
}

const Container: FC<Props> = ({ children }) => {
   return (
      <div className="flex flex-col mx-auto pt-20 sm:w-[320px] md:w-[480px] lg:w-[512px] xl:w-[576px]">
         {children}
         <Footer />
      </div>
   );
};

export default Container;
