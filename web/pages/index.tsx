import type { NextPage } from "next";
import About from "../components/About";
import Contact from "../components/Contact";
import Footer from "../components/Footer";
import Head from "../components/Head";
import Navbar from "../components/Navbar";
import Projects from "../components/Projects";
import Research from "../components/Research";
//@ts-ignore
import Fade from "react-reveal/Fade";
import Music from "../components/Music";

const Home: NextPage = () => {
  return (
    <div className="min-h-screen sm:bg-[#1E1E1E] font-main text-white w-full">
      <div className="flex flex-col items-center pt-12">
        <Navbar />
        <div className="sm:w-[282px] md:w-[563px] lg:w-[683px] xl:w-[1024px]">
          <Fade up delay={500} distance="25px">
            <Head />
            <About />
            <Research />
            <Projects />
            {/* <Music />
            <Contact /> */}
            <Footer />
          </Fade>
        </div>
      </div>
    </div>
  );
};

export default Home;
