import About from "@/app/components/About";
import Container from "@/app/components/Container";
import Experience from "@/app/components/Experience";
import Projects from "@/app/components/projects/Projects";

const Home = () => {
  return (
    <Container>
      <About />
      <br />
      <br />
      <Experience />
      <br />
      <br />
      <Projects />
      <br />
      <br />
    </Container>
  );
};

export default Home;
