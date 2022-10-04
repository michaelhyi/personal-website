//@ts-ignore
import Fade from "react-reveal/Fade";

const Head = () => {
  return (
    // <Fade up delay={800} distance="25px">
    //   {" "}
    <div className="flex items-center justify-between mt-12">
      <div>
        <div className="font-bold text-7xl">Michael Yi</div>
        <div className="font-semibold text-3xl mt-6">
          Portola High School '23
        </div>
        <div className="text-xl mt-6">
          {"VP of Sponsorships & Finance "}{" "}
          <a
            className="text-blue-400 hover:cursor-pointer duration-300 hover:opacity-50"
            href="https://health-hacks.tech/"
          >
            {"@ health{hacks}"}
          </a>
        </div>
        <div className="text-xl">
          Research Intern{" "}
          <a
            className="text-blue-400 hover:cursor-pointer duration-300 hover:opacity-50"
            href="https://sites.uci.edu/mcnaughtonlab/"
          >
            @ UCI McNaughton Lab
          </a>
        </div>
        <div className="text-xl">
          Founder{" "}
          <a
            className="text-blue-400 hover:cursor-pointer duration-300 hover:opacity-50"
            href="https://www.healthrelay.tech/"
          >
            @ HealthRelay
          </a>
        </div>
      </div>
      <img
        src="/Michael Yi.png"
        className="h-[300px] w-[300px] rounded-[150px]"
      />
    </div>
    // </Fade>
  );
};

export default Head;
