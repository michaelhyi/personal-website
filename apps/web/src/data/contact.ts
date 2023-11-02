import { AiFillGithub, AiFillLinkedin, AiOutlineMail } from "react-icons/ai";
import type Contact from "../types/contact";

export const contact: Contact[] = [
  {
    icon: AiOutlineMail,
    href: "mailto:michaelyi@gatech.edu",
  },
  {
    icon: AiFillGithub,
    href: "https://github.com/michaelhyi",
  },
  {
    icon: AiFillLinkedin,
    href: "https://www.linkedin.com/in/michaelhyi/",
  },
];
