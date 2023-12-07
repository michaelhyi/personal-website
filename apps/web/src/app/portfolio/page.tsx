import { Container } from "@personal-website/ui";
import Link from "next/link";
import { IoIosArrowBack } from "react-icons/io";
import Education from "@/components/portfolio/Education";
import Experience from "@/components/portfolio/Experience";
import Projects from "@/components/portfolio/Projects";
import { experience } from "@/data/experience";
import { projects } from "@/data/projects";

export default function Home() {
  return (
    <Container>
      <Link href="/" className="duration-500 hover:opacity-50">
        <IoIosArrowBack />
      </Link>
      <Experience data={experience} />
      <Education />
      <Projects data={projects} />
    </Container>
  );
}
