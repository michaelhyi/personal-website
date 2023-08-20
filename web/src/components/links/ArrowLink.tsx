import Link from "next/link";
import { AiOutlineArrowLeft, AiOutlineArrowRight } from "react-icons/ai";

interface Props {
  href: string;
  left: boolean;
  text: string;
  newTab?: boolean;
}

const ArrowLink: React.FC<Props> = ({ href, left, text, newTab = false }) => {
  return (
    <Link
      href={href}
      className="flex items-center gap-2 cursor-pointer duration-500 hover:opacity-50 text-pink-300 font-bold"
      rel={newTab ? "noopener noreferrer" : ""}
      target={newTab ? "_blank" : ""}
    >
      {left && <AiOutlineArrowLeft />} {text} {!left && <AiOutlineArrowRight />}
    </Link>
  );
};

export default ArrowLink;
