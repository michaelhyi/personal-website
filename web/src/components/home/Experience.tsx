import { experience } from "../../data/experience";
import Link from "./Link";

const Experience = () => {
  return (
    <div className="mt-12">
      <div className="font-bold text-2xl mb-4">Experience</div>
      {experience.map((v, i) => (
        <div key={i} className="flex mb-2">
          <div>{v.date}</div>
          <div className="absolute ml-36 font-normal">
            {v.position} <Link href={v.href}>@ {v.company}</Link>
          </div>
        </div>
      ))}
    </div>
  );
};

export default Experience;
