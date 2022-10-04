//@ts-ignore
import Fade from "react-reveal/Fade";
import NavbarLink from "./NavbarLink";

const Navbar = () => {
  return (
    <Fade up delay={200} distance="25px">
      <div className="flex pb-8">
        <div className="flex items-center space-x-8 ml-auto">
          <NavbarLink text="About" />
          <NavbarLink text="Portfolio" />
          <NavbarLink text="Misc" />
          <NavbarLink text="Contact" />
          <button className="flex text-xl justify-center items-center bg-blue-400 w-[125px] h-[62.5px] rounded-xl hover:cursor-pointer duration-300 hover:opacity-50">
            <a href="/Resume.pdf" target="_blank" rel="noreferrer">
              Resume
            </a>
          </button>
        </div>
      </div>
    </Fade>
  );
};

export default Navbar;
