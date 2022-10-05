import dynamic from "next/dynamic";
const NavbarLink = dynamic(() => import("./NavbarLink"), { ssr: false });

const Footer = () => {
  return (
    <div className="flex flex-col w-full items-center justify-center mt-24 text-center">
      <div className="grid grid-cols-3 sm:gap-x-12 md:gap-x-36 lg:gap-x-60 gap-x-96 gap-y-2 text-center">
        <NavbarLink text="About" />
        <NavbarLink text="Music" />
        <div className="sm:text-sm md:text-lg xl:text-xl hover:cursor-pointer duration-300 hover:opacity-25 text-center">
          <a
            href="mailto: 23yimichael@gmail.com"
            target="_blank"
            rel="noreferrer"
          >
            Email
          </a>
        </div>
        <NavbarLink text="Research" />
        <NavbarLink text="Contact" />
        <div className="sm:text-sm md:text-lg xl:text-xl hover:cursor-pointer duration-300 hover:opacity-25">
          <a
            href="https://www.linkedin.com/in/michael-yi-9a185a212/"
            target="_blank"
            rel="noreferrer"
          >
            LinkedIn
          </a>
        </div>
        <NavbarLink text="Projects" />
        <div className="sm:text-sm md:text-lg xl:text-xl hover:cursor-pointer duration-300 hover:opacity-25">
          <a href="/Resume.pdf" target="_blank" rel="noreferrer">
            Resume
          </a>
        </div>
        <div className="sm:text-sm md:text-lg xl:text-xl hover:cursor-pointer duration-300 hover:opacity-25">
          <a
            href="https://github.com/23yimichael"
            target="_blank"
            rel="noreferrer"
          >
            Github
          </a>
        </div>
      </div>
      <div className="mt-12 sm:text-base md:text-lg pb-12">
        © 2022 Michael Yi. All rights reserved.
      </div>
    </div>
  );
};

export default Footer;
