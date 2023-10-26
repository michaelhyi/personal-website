import Education from "@/components/home/Education";
import Container from "../components/Container";
import Experience from "../components/home/Experience";
import Head from "../components/home/Head";
import Projects from "../components/home/Projects";

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
