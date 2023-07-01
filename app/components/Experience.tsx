"use client";

import Link from "@/app/components/Link";
import { experience } from "@/app/data/experience";
import { useCallback, useState } from "react";

const Experience = () => {
  const [index, setIndex] = useState<number>(0);

  const handleClick = useCallback((i: number) => {
    setIndex(i);
  }, []);

  return (
    <div>
      <div className="font-bold text-2xl">Experience</div>
      <br />
      <div className="flex justify-between">
        {experience.map((v, i) => (
          <div
            key={v.company}
            onClick={() => handleClick(i)}
            className={`flex-grow text-center ${
              i === index
                ? "font-bold text-blue-500 border-blue-500 border-b-2"
                : "font-normal text-black border-b-2"
            } cursor-pointer duration-300 hover:opacity-50 hover:text-blue-500 hover:border-b-2 hover:border-blue-500`}
          >
            {v.company}
          </div>
        ))}
      </div>
      <br />
      <div className="flex justify-between">
        <div>
          {experience[index].position}{" "}
          <Link href={experience[index].href}>
            @ {experience[index].company}
          </Link>
        </div>
        <div>{experience[index].date}</div>
      </div>
      <ul className="mt-2">
        {experience[index].desc.map((v) => (
          <li
            dangerouslySetInnerHTML={{ __html: v }}
            key={v}
            className="list-disc ml-6"
          />
        ))}
      </ul>
    </div>
  );
};

export default Experience;
