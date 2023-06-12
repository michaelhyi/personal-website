//@ts-ignore
import Fade from "react-reveal/Fade";
import NavbarLink from "./NavbarLink";

const Navbar = () => {
  return (
    <Fade up delay={200} distance="25px">
      <div className="flex p-8 w-full sticky top-0 z-50 items-center bg-[#1E1E1E]">
        <div className="flex m-auto items-center sm:space-x-4 md:space-x-8 space-x-12 ">
          <NavbarLink text="About" />
          <NavbarLink text="Portfolio" />
          {/* <button className="flex sm:text-sm md:text-lg xl:text-xl justify-center items-center bg-blue-400 sm:w-[75px] sm:h-[37.5px] md:w-[100px] md:h-[50px] xl:w-[125px] xl:h-[62.5px] rounded-xl hover:cursor-pointer duration-300 hover:opacity-50">
            <a href="/Resume.pdf" target="_blank" rel="noreferrer">
              Resume
            </a>
          </button> */}
        </div>
      </div>
    </Fade>
  );
};

export default Navbar;
