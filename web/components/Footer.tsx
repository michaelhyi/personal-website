import dynamic from "next/dynamic";
const NavbarLink = dynamic(() => import("./NavbarLink"), { ssr: false });

const Footer = () => {
  return (
    <div className="flex flex-col items-center mt-24">
      <div className="grid grid-cols-3 gap-x-96 gap-y-2">
        <NavbarLink text="About" />
        <NavbarLink text="Misc" />
        <a
          href="mailto: 23yimichael@gmail.com"
          target="_blank"
          rel="noreferrer"
        >
          <NavbarLink text="Email" />
        </a>
        <NavbarLink text="Research" />
        <NavbarLink text="Contact" />
        <a
          href="https://www.linkedin.com/in/michael-yi-9a185a212/"
          target="_blank"
          rel="noreferrer"
        >
          <NavbarLink text="LinkedIn" />
        </a>
        <NavbarLink text="Portfolio" />
        <a href="/Resume.pdf" target="_blank" rel="noreferrer">
          <NavbarLink text="Resume" />
        </a>
        <a
          href="https://github.com/23yimichael"
          target="_blank"
          rel="noreferrer"
        >
          <NavbarLink text="Github" />
        </a>
      </div>
      <div className="mt-12 text-lg pb-12">
        © 2022 Michael Yi. All rights reserved.
      </div>
    </div>
  );
};

export default Footer;
