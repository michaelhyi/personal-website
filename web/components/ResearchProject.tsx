import React from "react";

interface Props {
  key: number;
  data: {
    title: string;
    authors: string;
    conference: string;
    abstract: string;
    poster: string;
    code: string;
  };
}

const ResearchProject: React.FC<Props> = ({ key, data }) => {
  return (
    <div className="flex flex-col">
      <div className="font-semibold text-3xl">{data.title}</div>
      <div className="font-medium mt-2 text-sm">{data.authors}</div>
      <div className="font-medium mt-2 text-sm">{data.conference}</div>
      <div className="sm:mx-auto md:mx-0 font-medium mt-2 text-sm flex">
        {data.abstract.length !== 0 && (
          <a
            className="text-blue-400 hover:cursor-pointer duration-300 hover:opacity-50"
            href={data.abstract}
            target="_blank"
            rel="noreferrer"
          >
            Abstract
          </a>
        )}
        {data.poster.length !== 0 && (
          <a
            className="flex text-blue-400 hover:cursor-pointer duration-300 hover:opacity-50"
            href={data.poster}
            target="_blank"
            rel="noreferrer"
          >
            {data.abstract.length !== 0 && (
              <div className="text-white">&nbsp;|&nbsp;</div>
            )}
            Poster
          </a>
        )}
        {data.code && (
          <a
            className="flex text-blue-400 hover:cursor-pointer duration-300 hover:opacity-50"
            href={data.code}
            target="_blank"
            rel="noreferrer"
          >
            <div className="text-white">&nbsp;|&nbsp;</div>
            Code
          </a>
        )}
      </div>
      <div className="sm:mt-16 md:mt-8" />
    </div>
  );
};

export default ResearchProject;
