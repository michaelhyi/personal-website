import dynamic from "next/dynamic";
import FooterLink from "./FooterLink";
const NavbarLink = dynamic(() => import("./NavbarLink"), { ssr: false });

const Footer = () => {
  return (
    <div className="flex flex-col w-full items-center justify-center mt-24 text-center">
      <div className="grid grid-cols-3 sm:gap-x-12 md:gap-x-36 lg:gap-x-60 gap-x-96 gap-y-2 text-center">
        <NavbarLink text="About" />
        <NavbarLink text="Music" />
        <FooterLink href="mailto: 23yimichael@gmail.com" text="Email" />
        <NavbarLink text="Research" />
        <NavbarLink text="Contact" />
        <FooterLink
          href="https://www.linkedin.com/in/23yimichael/"
          text="LinkedIn"
        />
        <NavbarLink text="Projects" />
        <FooterLink href="/Resume.pdf" text="Resume" />
        <FooterLink href="https://github.com/23yimichael" text="Github" />
      </div>
      <div className="mt-12 sm:text-base md:text-lg pb-12">
        © 2022 Michael Yi. All rights reserved.
      </div>
    </div>
  );
};

export default Footer;
