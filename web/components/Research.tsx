import { research } from "../data/research";
import ResearchProject from "./ResearchProject";

const Research = () => {
  return (
    <div id="portfolio" className="pt-8 sm:text-center md:text-left">
      <div className="font-bold text-5xl mt-24 mb-8">Research</div>
      {research.map((v, i) => (
        <ResearchProject key={i} data={v} />
      ))}
    </div>
  );
};

export default Research;
