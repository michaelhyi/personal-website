import About from "@/app/components/About";
import Container from "@/app/components/Container";
import Experience from "@/app/components/Experience";
import Projects from "@/app/components/Projects";

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
