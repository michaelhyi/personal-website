import Container from "../components/Container";
import Experience from "../components/home/Experience";
import Head from "../components/home/Head";
import Projects from "../components/home/Projects";
import Honors from "@/components/home/Honors";
import Education from "@/components/home/Education";

const Home = () => {
   return (
      <Container>
         <Head />
         <Experience />
         <Education />
         <Honors />
         <Projects />
      </Container>
   );
};

export default Home;
