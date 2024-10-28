const PROJECTS = [
    {
        name: "Personal Website",
        date: "Oct 2022 - Oct 2024",
        description: "",
        tech: "HTML, CSS, JS, Go",
        href: "https://michael-yi.com",
    },
    {
        name: "ScribeAI (1st Place Overall at HackGT)",
        date: "Oct 2023",
        description:
            "A HIPAA-compliant, decentralized healthcare management platform that uses AI to transform physical healthcare documents into electronic health records.",
        tech: "Java, Spring, PostgreSQL, Docker, Kubernetes, TypeScript, Next.js, Tailwind, Tesseract, OpenAI",
        image: "/assets/img/scribeai.png",
        href: "https://github.com/michaelhyi/scribeai",
    },
    {
        name: "Sales Invoice Automation Platform",
        date: "Apr - Jun 2024",
        description:
            "A platform for a logistics company that generates and bills invoices from Excel sales data.",
        tech: "Java, Spring, gRPC, Kafka, GraphQL, MySQL, AWS S3, Docker, React, Tailwind",
    },
    {
        name: "Film Blog",
        date: "Aug 2023 - Oct 2024",
        description: "A showcase of my passion for cinema.",
        tech: "Java, Go, Spring, MySQL, Redis, JUnit, React",
        href: "https://film.michael-yi.com/",
    },
    {
        name: "health{hacks} Website",
        date: "Jan - Jun 2023",
        description: "Assembling the future innovators of medicine.",
        tech: "TypeScript, Next.js, Tailwind, Node.js, Express.js, PostgreSQL, MongoDB, GraphQL",
        image: "/assets/img/hhacks.png",
        href: "https://www.joinhealthhacks.com/",
    },
    {
        name: "WeVote",
        date: "Oct 2022",
        description: "A platform built for a nonprofit that empowers and promotes voter registration.",
        tech: "TypeScript, Next.js, Tailwind",
        href: "https://wevote18.com/"
    },
    {
        name: "Enhancing Generative Commonsense Reasoning Using Image Cues",
        date: "Jun - Jul 2022",
        description:
            "An NLP + Vision model that generates coherent sentences given respective keywords (concepts) and a coresponding image.",
        tech: "Python, PyTorch",
        image: "/assets/img/shine.png",
        href: "https://github.com/michaelhyi/usc-shine",
    },
    {
        name: "Linkix",
        date: "Apr 2022",
        description: "A social media platform tailored for pre-professional students aiming to connect and collaborate.",
        tech: "TypeScript, Python, React Native, Flask, MongoDB",
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
        tech: "Python, JavaScript, Tensorflow, Keras, React Native, Firebase",
        href: "https://github.com/michaelhyi/chestray",
    },
    {
        name: "LeafX",
        date: "Jan 2022",
        description:
            "Best Environmental Hack @ Unlock Hacks. A powerful medical tool for plant health.",
        tech: "Python, JavaScript, Tensorflow, Keras, React Native",
        href: "https://github.com/michaelhyi/leafx",
    },
    {
        name: "HealthRelay",
        date: "Nov 2021 - Nov 2022",
        description:
            "Streamlining secure communication between radiologists and ordering physicians.",
        tech: "TypeScript, React Native, PostgreSQL, GraphQL",
        image: "/assets/img/healthrelay.png",
        href: "https://healthrelay.vercel.app/",
    },
    {
        name: "RetinaOCT",
        date: "Nov 2021 - Nov 2022",
        description:
            "Enhancing Retinal Abnormality Detection through Deep-Learning-based Optical Coherence Tomography Analysis",
        tech: "Python, Tensorflow, Keras, TypeScript, React Native, PostgreSQL, GraphQL",
        image: "/assets/img/retinaoct.png",
        href: "https://github.com/michaelhyi/retinaoct",
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
        name: "Medixly",
        date: "Jan 2021 - Oct 2022",
        description: "A mobile application that uses machine learning to diagnose skin cancer through images.",
        tech: "JavaScript, React Native",
    }
];

const HACKATHONS = [
    {
        name: "1st Place Overall at HackGT X",
        href: "https://devpost.com/software/scribeai",
    },
    {
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
        href: "https://www.youtube.com/watch?v=oFE0Inj-pr8",
    }
];

function getProjectComponent({ href, date, name, description, tech, image }) {
    const content = `
        <p class="date">${date}</p>
        <section class="details">
            <h3 class="name">${name}</h3>
            <p class="description">${description}</p>
            <p class="tech">${tech}</p>
            ${image ? `<img src="${image}" />` : ""}
        </section>
    `;

    if (href) {
        return `
            <a class="project-card" href="${href}" rel="noopener noreferrer" target="_blank">
                ${content}
            </a>
        `;
    }

    return `<div class="project-card">${content}</div>`;
}

function getHackathonComponent({ name, href }) {
    return `
        <a class="hackathon-card" href="${href}" rel="noopener noreferrer" target="_blank">
            ${name}
        </a>
    `;
}

const projectsHtml = PROJECTS.map(getProjectComponent).join("");
const hackathonsHtml = HACKATHONS.map(getHackathonComponent).join("");

document.getElementById("projects").innerHTML = projectsHtml;
document.getElementById("hackathons").innerHTML = hackathonsHtml;