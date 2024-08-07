import "./about.css";

import { BackButton, Center, Container, Hoverable } from "../../components";

export default function About() {
    return (
        <Container absoluteFooter>
            <Center>
                <section className="about-details">
                    <BackButton href="/" text="Home" />
                    <p>
                        Michael Yi is a software engineer currently interning at
                        T-Mobile. He&apos;s a junior studying Computer Science
                        at Georgia Tech, and he previously worked at Ardent Labs
                        and MegaEvolution.
                    </p>
                    <p>
                        Michael loves building. When he&apos;s not creating
                        software, he loves watching cinema and playing piano.
                    </p>
                    <p>
                        Reach him at&nbsp;
                        <Hoverable>
                            <a
                                href="mailto:contact@michael-yi.com"
                                className="about-contact"
                            >
                                contact@michael-yi.com
                            </a>
                        </Hoverable>
                        .
                    </p>
                </section>
            </Center>
        </Container>
    );
}
