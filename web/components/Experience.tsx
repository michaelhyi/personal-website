import React from "react";

interface Props {
  key: number;
  data: {
    position: string;
    organization: string;
    link: string;
  };
}

const Experience: React.FC<Props> = ({ key, data }) => {
  return (
    <div className="md:text-sm lg:text-base xl:text-xl">
      {data.position}{" "}
      <a
        className="text-blue-400 hover:cursor-pointer duration-300 hover:opacity-50"
        href={data.link}
        target="_blank"
        rel="noreferrer"
      >
        {"@ "}
        {data.organization}
      </a>
    </div>
  );
};

export default Experience;
