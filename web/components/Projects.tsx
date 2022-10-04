import Project from "./Project";

const Projects = () => {
  const data = [
    {
      name: "CarCam",
      desc: "Bridging the digital divide by providing mobile dashcam technology to low-income demographics.",
      logo: "/CarCam.png",
      video: "https://www.youtube.com/watch?v=oFE0Inj-pr8",
      code: "https://github.com/23yimichael/carcam",
    },
    {
      name: "ChestRay",
      desc: "A powerful bridge between doctors and patients. Coding Your Future Hackathon 2nd Place Winner.",
      video: "https://www.youtube.com/watch?v=QvViKKw8zeM",
      code: "https://github.com/23yimichael/chestray",
    },
    {
      name: "MelaModel",
      desc: "A simple, but powerful app that detects for abnormal growth on skin using Artificial Intelligence.",
      logo: "/MelaModel.png",
      devpost: "https://devpost.com/software/n-a-dek950",
      code: "https://github.com/23yimichael/melamodel",
    },
    {
      name: "LeafX  ",
      desc: `A simple, yet powerful medical tool for plants. Unlock Hacks "Best Environmental Hack" Winner.`,
      video: "https://www.youtube.com/watch?v=TsIxvBIPTZg",
      code: "https://github.com/23yimichael/leafx",
    },
  ];

  return (
    <div>
      <div className="font-bold text-5xl mt-24 mb-16">Projects</div>
      <div className="grid grid-cols-2 gap-8">
        {data.map((v) => (
          <Project data={v} />
        ))}
      </div>
    </div>
  );
};

export default Projects;
