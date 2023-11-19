import type { Project } from "types";

export const projects: Project[] = [
  {
    id: 9,
    name: "ScribeAI",
    date: "2023",
    description:
      "1st Place @ HackGT X. HIPAA-compliant, decentralized healthcare management platform that uses AI to transform physical healthcare documents into electronic health records.",
    tech: "Java, JavaScript, Spring, PostgreSQL, Docker, Kubernetes, React, Tesseract, OpenAI",
    image: "/projects/scribeai.png",
    href: "https://devpost.com/software/scribeai",
  },
  {
    id: 8,
    name: "health{hacks} Website",
    date: "2023",
    description: "Assembling the future innovators of medicine.",
    tech: "TypeScript, Next.js, Node.js, Express.js, PostgreSQL, MongoDB, GraphQL",
    image: "/projects/joinhealthhacks.png",
    href: "https://www.joinhealthhacks.com/",
  },
  {
    id: 7,
    name: "HealthRelay",
    date: "2021 - 2022",
    description:
      "Streamlining secure communication between radiologists and ordering physicians.",
    tech: "TypeScript, React Native, PostgreSQL, GraphQL",
    image: "/projects/healthrelay.png",
    href: "https://healthrelay.vercel.app/",
  },
  {
    id: 6,
    name: "RetinaOCT",
    date: "2021 - 2022",
    description:
      "Enhancing Retinal Abnormality Detection through Deep-Learning-based Optical Coherence Tomography Analysis",
    tech: "Python, Tensorflow, Keras, TypeScript, React Native, PostgreSQL, GraphQL",
    image: "/projects/retinaoct.png",
    href: "https://github.com/michaelhyi/retinaoct",
  },
  {
    id: 5,
    name: "Enhancing Generative Commonsense Reasoning Using Image Cues",
    date: "2022",
    description:
      "An NLP + Vision model that generates coherent sentences given respective keywords (concepts) and a coresponding image.",
    tech: "Python, PyTorch",
    image: "/projects/shine.png",
    href: "https://github.com/michaelhyi/usc-shine",
  },
  {
    id: 4,
    name: "CarCam",
    date: "2021",
    description:
      "3rd Place @ 2021 Congressional App Challenge CA45 District. Bridging the digital divide by providing powerful dashcam technology to your phone.",
    tech: "JavaScript, React Native",
    href: "https://github.com/michaelhyi/carcam",
  },
  {
    id: 3,
    name: "MelaModel",
    date: "2022",
    description:
      "Best Access to Healthcare Hack @ PennApps Health Hacks. An image-recognition software that differentiates between benign and malignant skin moles using Artificial Intelligence.",
    tech: "Python, Tensorflow, Keras, TypeScript, React Native, Node.js, AWS Lambda, AWS DynamoDB, AWS CloudWatch",
    href: "https://github.com/michaelhyi/melamodel",
  },
  {
    id: 2,
    name: "ChestRay",
    date: "2022",
    description:
      "2nd Place @ Coding Your Future Hackathon. A powerful bridge between doctors, patients, and Artificial Intelligence based x-ray diagnosis.",
    tech: "Python, Tensorflow, Keras, JavaScript, React Native, Firebase",
    href: "https://github.com/michaelhyi/chestray",
  },
  {
    id: 1,
    name: "LeafX",
    date: "2022",
    description:
      "Best Environmental Hack @ Unlock Hacks. A powerful medical tool for plant health.",
    tech: "Python, Tensorflow, Keras, JavaScript, React Native",
    href: "https://github.com/michaelhyi/leafx",
  },
];
