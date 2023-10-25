"use client";

import { FC } from "react";

interface Props {
   absolute?: boolean;
}

const Footer: FC<Props> = ({ absolute = false }) => {
   return (
      <div
         className={`${
            absolute ? "absolute bottom-4 left-0 right-0" : "mt-12 pb-4"
         } text-center text-[10px] text-neutral-400`}
      >
         &copy; 2023 Michael Yi, All Rights Reserved.
      </div>
   );
};

export default Footer;
