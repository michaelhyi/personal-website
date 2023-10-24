"use client";

import { FC } from "react";

interface Props {
   text: string;
}

const Error: FC<Props> = ({ text }) => {
   return <div className="mt-4 text-red-500">{text}</div>;
};

export default Error;
