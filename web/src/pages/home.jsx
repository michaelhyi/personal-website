import Container from "../components/Container";
import Center from "../components/Center";
import Hoverable from "../components/Hoverable";

const LINKS = [
    {
        name: "About",
        href: "/about",
    },
    {
        name: "Portfolio",
        href: "/portfolio",
    },
    {
        name: "Blog",
        href: "/blog",
    },
    {
        name: "Resume",
        href: "/Resume.pdf",
    },
];

export default function Home() {
    return (
        <Container absoluteFooter>
            <Center>
                <div className="flex flex-col items-center">
                    <img
                        src="/michael.png"
                        alt="michael"
                        className="h-[100px] w-[100px] rounded-full"
                    />
                    <div className="mt-4 text-2xl font-medium">Michael Yi</div>
                    <div className="mt-1 text-xs font-light text-neutral-400">
                        Software Engineer
                    </div>
                    <div className="flex mt-2 text-[10px]">
                        {LINKS.map((v, i) => (
                            <Hoverable key={v.name}>
                                {i !== 0 && <>&nbsp;&nbsp;&#183;&nbsp;&nbsp;</>}
                                <a href={v.href}>{v.name}</a>
                            </Hoverable>
                        ))}
                    </div>
                </div>
            </Center>
        </Container>
    );
}
