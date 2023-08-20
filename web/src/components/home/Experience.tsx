import { experience } from "../../data/experience";
import ArrowLink from "../links/ArrowLink";
import Link from "../links/Link";

const Experience = () => {
  return (
    <div className="mt-12">
      <div className="flex items-center justify-between mb-8">
        <div className="text-xl font-semibold">Experience</div>
        <ArrowLink href="/Resume.pdf" left={false} text="See Resume" newTab />
      </div>
      {experience.map((v, i) => (
        <div key={i} className="flex mb-2">
          <div className="font-extralight opacity-50">{v.date}</div>
          <div className="absolute ml-36">
            {v.position} <Link href={v.href}>@ {v.company}</Link>
          </div>
        </div>
      ))}
    </div>
  );
};

export default Experience;
