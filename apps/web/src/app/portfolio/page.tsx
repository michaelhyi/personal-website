import { IoIosArrowBack } from "react-icons/io";
import Link from "next/link";
import { readAllExperiences, readAllProjects } from "services";
import { Container } from "ui";
import Education from "@/components/portfolio/Education";
import Experience from "@/components/portfolio/Experience";
import Projects from "@/components/portfolio/Projects";

const Home = async () => {
  const experienceData = await readAllExperiences();
  const projectsData = await readAllProjects();

  return (
    <Container>
      <Link href="/" className="duration-500 hover:opacity-50">
        <IoIosArrowBack />
      </Link>
      <Experience data={experienceData} />
      <Education />
      <Projects data={projectsData} />
    </Container>
  );
};

export default Home;
