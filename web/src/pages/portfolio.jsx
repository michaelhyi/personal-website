import BackButton from "../components/BackButton";
import Container from "../components/Container";
import Hoverable from "../components/Hoverable";

const PROJECTS = [
  {
    name: "ScribeAI",
    date: "Oct 2023",
    description:
      "1st Place @ HackGT X. HIPAA-compliant, decentralized healthcare management platform that uses AI to transform physical healthcare documents into electronic health records.",
    tech: "Java, JavaScript, Spring, PostgreSQL, Docker, Kubernetes, React, Tesseract, OpenAI",
    image: require("../assets/projects/scribeai.png"),
    href: "https://devpost.com/software/scribeai",
  },
  {
    name: "health{hacks} Website",
    date: "Jan - Jun 2023",
    description: "Assembling the future innovators of medicine.",
    tech: "TypeScript, Next.js, Node.js, Express.js, PostgreSQL, MongoDB, GraphQL",
    image: require("../assets/projects/hhacks.png"),
    href: "https://www.joinhealthhacks.com/",
  },
  {
    name: "HealthRelay",
    date: "Nov 2021 - Nov 2022",
    description:
      "Streamlining secure communication between radiologists and ordering physicians.",
    tech: "TypeScript, React Native, PostgreSQL, GraphQL",
    image: require("../assets/projects/healthrelay.png"),
    href: "https://healthrelay.vercel.app/",
  },
  {
    name: "RetinaOCT",
    date: "Nov 2021 - Nov 2022",
    description:
      "Enhancing Retinal Abnormality Detection through Deep-Learning-based Optical Coherence Tomography Analysis",
    tech: "Python, Tensorflow, Keras, TypeScript, React Native, PostgreSQL, GraphQL",
    image: require("../assets/projects/retinaoct.png"),
    href: "https://github.com/michaelhyi/retinaoct",
  },
  {
    name: "Enhancing Generative Commonsense Reasoning Using Image Cues",
    date: "Jun - Jul 2022",
    description:
      "An NLP + Vision model that generates coherent sentences given respective keywords (concepts) and a coresponding image.",
    tech: "Python, PyTorch",
    image: require("../assets/projects/shine.png"),
    href: "https://github.com/michaelhyi/usc-shine",
  },
  {
    name: "CarCam",
    date: "Jun - Oct 2021",
    description:
      "3rd Place @ 2021 Congressional App Challenge CA45 District. Bridging the digital divide by providing powerful dashcam technology to your phone.",
    tech: "JavaScript, React Native",
    href: "https://github.com/michaelhyi/carcam",
  },
  {
    name: "MelaModel",
    date: "Mar 2022",
    description:
      "Best Access to Healthcare Hack @ PennApps Health Hacks. An image-recognition software that differentiates between benign and malignant skin moles using Artificial Intelligence.",
    tech: "Python, Tensorflow, Keras, TypeScript, React Native, Node.js, AWS Lambda, AWS DynamoDB, AWS CloudWatch",
    href: "https://github.com/michaelhyi/melamodel",
  },
  {
    name: "ChestRay",
    date: "Jan 2022",
    description:
      "2nd Place @ Coding Your Future Hackathon. A powerful bridge between doctors, patients, and Artificial Intelligence based x-ray diagnosis.",
    tech: "Python, Tensorflow, Keras, JavaScript, React Native, Firebase",
    href: "https://github.com/michaelhyi/chestray",
  },
  {
    name: "LeafX",
    date: "Jan 2022",
    description:
      "Best Environmental Hack @ Unlock Hacks. A powerful medical tool for plant health.",
    tech: "Python, Tensorflow, Keras, JavaScript, React Native",
    href: "https://github.com/michaelhyi/leafx",
  },
];

export default function Home() {
  return (
    <Container>
      <BackButton href="/" text="Home" />
      <div className="mt-10 flex flex-col gap-8">
        {PROJECTS.map((project) => (
          <Hoverable key={project.name}>
            <a rel="noopener noreferrer" target="_blank" href={project.href}>
              <div className="flex sm:flex-col md:flex-row">
                <div className="w-56 text-[13px] font-light text-neutral-400 sm:mb-2 md:mb-0">
                  {project.date}
                </div>
                <div className="w-full">
                  <div className="flex gap-1 font-semibold">{project.name}</div>
                  <div className="mt-1 text-xs text-neutral-400">
                    {project.description}
                  </div>
                  <div className="mt-2 text-[10px] text-neutral-300 font-light">
                    {project.tech}
                  </div>
                  {project.image !== undefined &&
                    project.image.length !== 0 && (
                      <img
                        className="mt-4 rounded-md shadow-md"
                        src={project.image}
                        alt={project.name}
                        width={200}
                        height={120}
                      />
                    )}
                </div>
              </div>
            </a>
          </Hoverable>
        ))}
      </div>
    </Container>
  );
}
