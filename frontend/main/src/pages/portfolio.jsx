import Footer from "../components/Footer";
import Project from "../components/Project";
import "../css/portfolio.css";
import HEALTHRELAY from "../img/healthrelay.png";
import HEALTHHACKS from "../img/hhacks.png";
import RETINAOCT from "../img/retinaoct.png";
import SCRIBEAI from "../img/scribeai.png";
import SHINE from "../img/shine.png";

const PROJECTS = [
    {
        name: "Film Blog",
        date: "Aug 2023 - Present",
        description: "A showcase of my passion for cinema.",
        tech: "Java, Spring, MySQL, Redis, JUnit, React",
        href: "/blog",
    },
    {
        name: "ScribeAI",
        date: "Oct 2023",
        description:
            "1st Place @ HackGT X. HIPAA-compliant, decentralized healthcare management platform that uses AI to transform physical healthcare documents into electronic health records.",
        tech: "Java, JavaScript, Spring, PostgreSQL, Docker, Kubernetes, React, Tesseract, OpenAI",
        image: SCRIBEAI,
        href: "https://devpost.com/software/scribeai",
    },
    {
        name: "health{hacks} Website",
        date: "Jan - Jun 2023",
        description: "Assembling the future innovators of medicine.",
        tech: "TypeScript, Next.js, Node.js, Express.js, PostgreSQL, MongoDB, GraphQL",
        image: HEALTHHACKS,
        href: "https://www.joinhealthhacks.com/",
    },
    {
        name: "HealthRelay",
        date: "Nov 2021 - Nov 2022",
        description:
            "Streamlining secure communication between radiologists and ordering physicians.",
        tech: "TypeScript, React Native, PostgreSQL, GraphQL",
        image: HEALTHRELAY,
        href: "https://healthrelay.vercel.app/",
    },
    {
        name: "RetinaOCT",
        date: "Nov 2021 - Nov 2022",
        description:
            "Enhancing Retinal Abnormality Detection through Deep-Learning-based Optical Coherence Tomography Analysis",
        tech: "Python, Tensorflow, Keras, TypeScript, React Native, PostgreSQL, GraphQL",
        image: RETINAOCT,
        href: "https://github.com/michaelhyi/retinaoct",
    },
    {
        name: "Enhancing Generative Commonsense Reasoning Using Image Cues",
        date: "Jun - Jul 2022",
        description:
            "An NLP + Vision model that generates coherent sentences given respective keywords (concepts) and a coresponding image.",
        tech: "Python, PyTorch",
        image: SHINE,
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

const HACKATHONS = [
    {
        name: "1st Place at HackGT X",
        href: "https://devpost.com/software/scribeai",
    },
    {
        // eslint-disable-next-line quotes -- must use backtick for double quotes within string
        name: `Best Access to Healthcare Hack at PennApps "Health Hacks"`,
        href: "https://devpost.com/software/n-a-dek950",
    },
    {
        name: "2nd Place at Coding Your Future Hackathon",
        href: "https://devpost.com/software/chestray",
    },
    {
        name: "Best Environmental Hack at Unlock Hacks",
        href: "https://devpost.com/software/leafx",
    },
    {
        name: "3rd Place at CA45 Congressional App Challenge",
        href: "https://github.com/michaelhyi/carcam",
    },
];

export default function Portfolio() {
    return (
        <main>
            <section className="content">
                <a href="/" className="back-arrow">
                    &#8592;
                </a>
                <section className="portfolio-projects">
                    <h1 className="portfolio-projects-header">Projects</h1>
                    {PROJECTS.map((project) => (
                        <Project key={project.name} project={project} />
                    ))}
                </section>
                <section className="portfolio-hackathons">
                    <h1 className="portfolio-hackathons-header">Hackathons</h1>
                    {HACKATHONS.map((hackathon) => (
                        <a
                            className="portfolio-hackathons-card-anchor"
                            key={hackathon.name}
                            href={hackathon.href}
                            rel="noopener noreferrer"
                            target="_blank"
                        >
                            {hackathon.name}
                            <span className="portfolio-hackathons-card-anchor-arrow">
                                &#8599;
                            </span>
                        </a>
                    ))}
                </section>
            </section>
            <Footer />
        </main>
    );
}
