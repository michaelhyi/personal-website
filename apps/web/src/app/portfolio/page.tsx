import Container from "@/components/Container";
import Education from "@/components/home/Education";
import Experience from "@/components/home/Experience";
import Projects from "@/components/home/Projects";

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
