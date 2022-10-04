import type { NextPage } from "next";
import About from "../components/About";
import Contact from "../components/Contact";
import Footer from "../components/Footer";
import Head from "../components/Head";
import Misc from "../components/Misc";
import Navbar from "../components/Navbar";
import Projects from "../components/Projects";
import Research from "../components/Research";
//@ts-ignore
import Fade from "react-reveal/Fade";

const Home: NextPage = () => {
  return (
    <div className="min-h-screen bg-[#1E1E1E] font-main text-white">
      <div className="flex justify-center pt-12">
        <div className="w-[1024px]">
          <Navbar />
          <Fade up delay={500} distance="25px">
            <Head />
            <About />
            <Research />
            <Projects />
            <Misc />
            <Contact />
            <Footer />
          </Fade>
        </div>
      </div>
    </div>
  );
};

export default Home;
