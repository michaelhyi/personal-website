"use client";

import Footer from "@/app/components/Footer";
import { ReactNode } from "react";

interface Props {
  children: ReactNode;
}

const Container: React.FC<Props> = ({ children }) => {
  return (
    <div>
      <div className="flex flex-col min-h-screen w-full items-center justify-center">
        <div className="sm:w-[320px] md:w-[480px] lg:w-[512px] xl:w-[768px]">
          {children}
        </div>
      </div>
      <br/>
      <Footer />
    </div>
  );
};

export default Container;
