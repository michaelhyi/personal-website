"use client";

import { FC, ReactNode } from "react";

interface Props {
   title: string;
   children: ReactNode;
   nonDesc?: boolean;
}

const Section: FC<Props> = ({ title, children, nonDesc = false }) => {
   return (
      <div className="mt-10">
         <div className="font-normal">{title}</div>
         <div className={`mt-6 flex flex-col ${nonDesc ? "gap-4" : "gap-8"}`}>{children}</div>
      </div>
   );
};

export default Section;
