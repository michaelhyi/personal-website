import React from "react";

interface Props {
  data: {
    name: string;
    desc: string;
    logo?: string;
    video?: string;
    devpost?: string;
    code: string;
  };
}

const Project: React.FC<Props> = ({ data }) => {
  return (
    <div className="flex flex-col items-center text-center bg-zinc-800 h-[500px] w-[500px] rounded-3xl">
      <div className="font-semibold text-4xl mt-12">{data.name}</div>
      <div className="mt-4 text-sm w-96">{data.desc}</div>
      <div className="flex h-[200px] w-[200px] rounded-xl bg-white items-center justify-center mt-8">
        {data.logo && <img src={data.logo} className="h-[150px] w-[150px]" />}
      </div>

      <div className="font-medium text-sm mt-12">
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
