"use client";

import { FC, ReactNode } from "react";

interface Props {
   title: string;
   children: ReactNode;
}

const Section: FC<Props> = ({ title, children }) => {
   return (
      <div className="mt-10">
         <div className="font-normal">{title}</div>
         <div className="mt-6 flex flex-col gap-8">{children}</div>
      </div>
   );
};

export default Section;
