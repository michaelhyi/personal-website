import { useEffect } from "react";
import Container from "../components/Container";
import About from "../components/home/About";
import Experience from "../components/home/Experience";
import Projects from "../components/home/Projects";

const Home = () => {
  useEffect(() => {
    const res = fetch("http://localhost:8080/api/v1/post");
    console.log(res);
  }, []);
  return (
    <Container>
      <About />
      <Experience />
      <Projects />
    </Container>
  );
};

export default Home;
