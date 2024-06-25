import "./home.css";

import { Container, Center, Hoverable } from "../../components";
import HERO_LINKS from "../../utils/constants/hero-links";

export default function Home() {
    return (
        <Container absoluteFooter>
            <Center className="home-section">
                <img
                    src="/michael.png"
                    alt="michael"
                    className="home-headshot"
                />
                <h2 className="home-header">Michael Yi</h2>
                <p className="home-subheader">
                    Software Engineer
                </p>
                <section className="home-links home-links-text">
                    {HERO_LINKS.map((v, i) => (
                        <Hoverable key={v.name}>
                            {i !== 0 && <>&nbsp;&nbsp;&#183;&nbsp;&nbsp;</>}
                            <a href={v.href}>{v.name}</a>
                        </Hoverable>
                    ))}
                </section>
            </Center>
        </Container>
    );
}
