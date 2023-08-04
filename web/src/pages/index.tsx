import About from "../components/About";
import Container from "../components/Container";
import Projects from "../components/projects/Projects";

const Home = () => {
  return (
    <Container>
      <About />
      <br />
      <br />
      <Projects />
      <br />
      <br />
    </Container>
  );
};

export default Home;
