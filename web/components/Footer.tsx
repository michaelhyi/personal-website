import dynamic from "next/dynamic";
const NavbarLink = dynamic(() => import("./NavbarLink"), { ssr: false });

const Footer = () => {
  return (
    <div className="flex flex-col items-center mt-24">
      <div className="grid grid-cols-3 gap-x-96 gap-y-2">
        <NavbarLink text="About" />
        <NavbarLink text="Misc" />
        <div className="text-xl hover:cursor-pointer duration-300 hover:opacity-25">
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
        <div className="text-xl hover:cursor-pointer duration-300 hover:opacity-25">
          <a
            href="https://www.linkedin.com/in/michael-yi-9a185a212/"
            target="_blank"
            rel="noreferrer"
          >
            LinkedIn
          </a>
        </div>
        <NavbarLink text="Projects" />
        <div className="text-xl hover:cursor-pointer duration-300 hover:opacity-25">
          <a href="/Resume.pdf" target="_blank" rel="noreferrer">
            Resume
          </a>
        </div>
        <div className="text-xl hover:cursor-pointer duration-300 hover:opacity-25">
          <a
            href="https://github.com/23yimichael"
            target="_blank"
            rel="noreferrer"
          >
            Github
          </a>
        </div>
      </div>
      <div className="mt-12 text-lg pb-12">
        © 2022 Michael Yi. All rights reserved.
      </div>
    </div>
  );
};

export default Footer;
