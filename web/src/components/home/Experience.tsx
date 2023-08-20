import { experience } from "../../data/experience";
import ArrowLink from "../links/ArrowLink";
import Link from "../links/Link";

const Experience = () => {
  return (
    <div className="mt-12">
      <div className="flex items-center justify-between mb-8">
        <div className="sm:text-lg md:text-xl font-semibold">Experience</div>
        <ArrowLink href="/Resume.pdf" left={false} text="Resume" newTab />
      </div>
      {experience.map((v, i) => (
        <div key={i} className="flex mb-2">
          <div className="font-extralight opacity-50 sm:text-xs md:text-sm">
            {v.date}
          </div>
          <div className="absolute sm:ml-24 md:ml-36 sm:text-xs md:text-sm">
            {v.position} <Link href={v.href}>@ {v.company}</Link>
          </div>
        </div>
      ))}
    </div>
  );
};

export default Experience;
