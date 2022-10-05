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
    <div>
      <div className="font-semibold text-3xl">{data.title}</div>
      <div className="font-medium mt-2 text-sm">{data.authors}</div>
      <div className="font-medium mt-2 text-sm">{data.conference}</div>
      <div className="font-medium mt-2 text-sm">
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
            className="text-blue-400 hover:cursor-pointer duration-300 hover:opacity-50"
            href={data.poster}
            target="_blank"
            rel="noreferrer"
          >
            Poster
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
      <div className="sm:mt-16 md:mt-8" />
    </div>
  );
};

export default ResearchProject;
