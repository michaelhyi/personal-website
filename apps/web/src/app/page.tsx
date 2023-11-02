import Container from "../components/container";
import Education from "../components/home/education";
import Experience from "../components/home/experience";
import Head from "../components/home/head";
import Projects from "../components/home/projects";

const Home = () => {
  return (
    <Container>
      <Head />
      <Experience />
      <Education />
      <Projects />
    </Container>
  );
};

export default Home;
