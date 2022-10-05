import React from "react";

interface Props {
  key: number;
  data: {
    name: string;
    desc: string;
    logo?: string;
    video?: string;
    devpost?: string;
    code: string;
  };
}

const Project: React.FC<Props> = ({ key, data }) => {
  return (
    <div className="flex flex-col items-center text-center bg-zinc-800 sm:h-[350px] sm:w-[350px] md:h-[500px] md:w-[500px] rounded-3xl w-full">
      <div className="font-semibold sm:text-2xl md:text-4xl sm:mt-8 md:mt-12">
        {data.name}
      </div>
      <div className="mt-4 sm:text-xs sm:w-80 md:text-sm md:w-96">
        {data.desc}
      </div>
      <div className="flex sm:h-[150px] sm:w-[150px] md:h-[200px] md:w-[200px] rounded-xl bg-white items-center justify-center mt-8">
        {data.logo && (
          <img
            src={data.logo}
            className="sm:h-[112.5px] sm:w-[112.5px] md:h-[150px] md:w-[150px]"
          />
        )}
      </div>
      <div className="font-medium text-sm sm:mt-4 md:mt-12">
        {data.video && (
          <a
            className="text-blue-400 hover:cursor-pointer duration-300 hover:opacity-50"
            href={data.video}
            target="_blank"
            rel="noreferrer"
          >
            Video
          </a>
        )}
        {data.devpost && (
          <a
            className="text-blue-400 hover:cursor-pointer duration-300 hover:opacity-50"
            href={data.devpost}
            target="_blank"
            rel="noreferrer"
          >
            Devpost
          </a>
        )}{" "}
        |{" "}
        <a
          className="text-blue-400 hover:cursor-pointer duration-300 hover:opacity-50"
          href={data.code}
          target="_blank"
          rel="noreferrer"
        >
          Code
        </a>
      </div>
    </div>
  );
};

export default Project;
