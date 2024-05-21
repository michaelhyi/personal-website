import BackButton from "../components/BackButton";
import Center from "../components/Center";
import Container from "../components/Container";
import Hoverable from "../components/Hoverable";

export default function About() {
    return (
        <Container absoluteFooter>
            <Center>
                <section className="flex flex-col text-left text-[13px] sm:w-[360px] md:w-[576px]">
                    <BackButton href="/" text="Home" />
                    <br />
                    <p>
                        Michael Yi is a software engineer currently interning at
                        T-Mobile. He&apos;s a junior studying Computer Science
                        at Georgia Tech, and he previously worked at Ardent Labs
                        and MegaEvolution.
                    </p>
                    <br />
                    <p>
                        Michael loves building. When he&apos;s not creating
                        software, he loves watching cinema and playing piano.
                    </p>
                    <br />
                    <p>
                        Reach him at&nbsp;
                        <Hoverable>
                            <a
                                href="mailto:contact@michael-yi.com"
                                className="underline text-neutral-400"
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
