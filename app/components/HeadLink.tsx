"use client";

import Link from "@/app/components/Link";
import { IconType } from "react-icons";

interface Props {
  icon: IconType;
  href: string;
  text: string;
}

const HeadLink: React.FC<Props> = ({ icon: Icon, href, text }) => {
  return (
    <div className="flex gap-2 items-center">
      <Icon size={20} className="text-blue-500" />
      <Link href={href}>{text}</Link>
    </div>
  );
};

export default HeadLink;
