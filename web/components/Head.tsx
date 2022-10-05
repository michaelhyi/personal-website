import { experience } from "../data/experience";
import Experience from "./Experience";

const Head = () => {
  return (
    <div className="flex sm:text-center md:text-left sm:flex-col-reverse md:flex-row items-center justify-between mt-12">
      <div>
        <div className="sm:mt-8 font-bold sm:text-4xl lg:text-5xl xl:text-7xl">
          Michael Yi
        </div>
        <div className="font-semibold md:text-xl xl:text-3xl md:mt-4 lg:mt-6">
          {"Portola High School '23"}
        </div>
        <div className="mt-6" />
        {experience.map((v, i) => (
          <Experience key={i} data={v} />
        ))}
      </div>
      <img
        src="/Michael Yi.png"
        className="sm:h-[200px] sm:w-[200px] lg:h-[250px] lg:w-[250px] xl:h-[300px] xl:w-[300px] rounded-full"
      />
    </div>
  );
};

export default Head;
