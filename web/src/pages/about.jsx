import BackButton from "../components/BackButton";
import Center from "../components/Center";
import Container from "../components/Container";
import Hoverable from "../components/Hoverable";

export default function About() {
    return (
        <Container absoluteFooter>
            <Center>
                <div className="flex flex-col text-left text-[13px] gap-4 sm:w-[360px] md:w-[576px]">
                    <BackButton href="/" text="Home" />
                    <div>
                        Michael Yi is a software engineer and an incoming intern
                        at T-Mobile. He&apos;s currently studying Computer
                        Science at Georgia Tech and previously interned at
                        Ardent Labs and MegaEvolution.
                    </div>
                    <div>
                        Michael loves building. When he&apos;s not creating
                        software, he loves watching cinema.
                    </div>
                    <div className="flex">
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
                    </div>
                </div>
            </Center>
        </Container>
    );
}
