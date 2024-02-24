import { motion } from "framer-motion";
import { AiFillGithub, AiFillLinkedin } from "react-icons/ai";
import Hoverable from "./Hoverable";

const FOOTER_LINKS = [
  {
    href: "https://github.com/michaelhyi/",
    icon: AiFillGithub,
  },
  {
    href: "https://www.linkedin.com/in/michaelhyi/",
    icon: AiFillLinkedin,
  },
];

export default function Container({ children, absoluteFooter = false }) {
  return (
    <div className="bg-neutral-900 text-white min-h-screen">
      <motion.div
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ duration: 0.75 }}
        className="flex flex-col pt-20 mx-auto sm:w-[320px] md:w-[480px] lg:w-[512px] xl:w-[768px]"
      >
        {children}
        <div
          id="footer"
          className={`flex 
                  flex-col
                  items-center
                  gap-3
                  text-[10px]
                  ${
                    absoluteFooter
                      ? "absolute bottom-4 left-0 right-0"
                      : "mt-16 pb-4"
                  }
                `}
        >
          <div className="flex gap-2">
            {FOOTER_LINKS.map(({ href, icon: Icon }) => (
              <Hoverable key={href}>
                <a
                  label="footer link"
                  href={href}
                  rel="noopener noreferrer"
                  target="_blank"
                >
                  <Icon size={15} />
                </a>
              </Hoverable>
            ))}
          </div>
          <div>&copy; 2023 Michael Yi, All Rights Reserved.</div>
        </div>
      </motion.div>
    </div>
  );
}
