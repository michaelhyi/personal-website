const Research = () => {
  return (
    <div id="portfolio">
      <div className="font-bold text-5xl mt-24 mb-8">Research</div>
      <div>
        <div className="font-semibold text-3xl">
          RetinaOCT: Enhancing Retinal Abnormality Detection through
          Deep-Learning-based Optical Coherence Tomography Analysis
        </div>
        <div className="font-medium mt-2 text-sm">
          Hwanjun Yi (Portola High School), John I. Woo (Portola High School),
          Ian Park (Northwood High School), Hwanseok Yi (Portola High School),
          Shikhar N. Sahaai (Northwood High School)
        </div>
        <div className="font-medium mt-2 text-sm">
          2022 Sigma Xi International Forum on Research Excellence Student
          Summit Research Conference
        </div>
        <div className="font-medium mt-2 text-sm">
          <a
            className="text-blue-400 hover:cursor-pointer duration-300 hover:opacity-50"
            href="/RetinaOCT Abstract.pdf"
            target="_blank"
            rel="noreferrer"
          >
            Abstract
          </a>{" "}
          |{" "}
          <a
            className="text-blue-400 hover:cursor-pointer duration-300 hover:opacity-50"
            href="https://github.com/23yimichael/retinaoct"
            target="_blank"
            rel="noreferrer"
          >
            Code
          </a>
        </div>
      </div>
      <div className="mt-8">
        <div className="font-semibold text-3xl">
          Enhancing Generative Commonsense Reasoning Using Image Cues
        </div>
        <div className="font-medium mt-2 text-sm">
          Hwanjun (Michael) Yi, Soumya Sanyal, Xiang Ren
        </div>
        <div className="font-medium mt-2 text-sm">
          2022 USC Summer High-School Intensive in Next-Generation Engineering
          (SHINE) Program
        </div>
        <div className="font-medium mt-2 text-sm">
          <a
            className="text-blue-400 hover:cursor-pointer duration-300 hover:opacity-50"
            href="/S22-Yi-M-Poster-Final.pdf"
            target="_blank"
            rel="noreferrer"
          >
            Poster
          </a>{" "}
          |{" "}
          <a
            className="text-blue-400 hover:cursor-pointer duration-300 hover:opacity-50"
            href="https://github.com/23yimichael/usc-shine"
            target="_blank"
            rel="noreferrer"
          >
            Code
          </a>
        </div>
      </div>
    </div>
  );
};

export default Research;
