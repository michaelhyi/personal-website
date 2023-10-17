import Container from "./components/Container";
import About from "./components/home/About";
import Experience from "./components/home/Experience";
import Projects from "./components/home/Projects";

const Home = () => {
  return (
    <Container>
      <About />
      <Experience />
      <Projects />
    </Container>
  );
};

export default Home;
