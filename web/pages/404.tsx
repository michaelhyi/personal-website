//@ts-ignore
import Fade from "react-reveal/Fade";

const Custom404 = () => {
  return (
    <div className="flex flex-col h-screen sm:bg-[#1E1E1E] font-main text-white w-full justify-center items-center">
      <Fade up delay={500} distance="25px">
        <div>404 | Page not found.</div>
        <a
          className="text-blue-400 hover:cursor-pointer duration-300 hover:opacity-50 font-semibold mt-2"
          href="https://michael-yi.com"
        >
          Back To Home
        </a>
        <div className="absolute bottom-0 pb-12">
          © 2022 Michael Yi. All rights reserved.
        </div>
      </Fade>
    </div>
  );
};

export default Custom404;
