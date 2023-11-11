import Container from "@/components/Container";
import Education from "@/components/portfolio/Education";
import Experience from "@/components/portfolio/Experience";
import Projects from "@/components/portfolio/Projects";

const Home = () => {
  return (
    <Container>
      <Experience />
      <Education />
      <Projects />
    </Container>
  );
};

export default Home;
