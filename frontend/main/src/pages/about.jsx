import "../css/about.css";

import BackButton from "../components/BackButton";
import Container from "../components/Container";

export default function About() {
    return (
        <Container absoluteFooter>
            <div className="center">
                <section className="about-details">
                    <BackButton href="/" />
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
                        <a
                            href="mailto:contact@michael-yi.com"
                            className="about-contact hoverable"
                        >
                            contact@michael-yi.com
                        </a>
                        .
                    </p>
                </section>
            </div>
        </Container>
    );
}
