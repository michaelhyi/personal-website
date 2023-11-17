import { IoIosArrowBack } from "react-icons/io";
import Link from "next/link";
import { Container } from "ui";
import Education from "@/components/portfolio/Education";
import Experience from "@/components/portfolio/Experience";
import Projects from "@/components/portfolio/Projects";

const Home = () => {
  return (
    <Container>
      <Link href="/" className="duration-500 hover:opacity-50">
        <IoIosArrowBack />
      </Link>
      <Experience />
      <Education />
      <Projects />
    </Container>
  );
};

export default Home;
