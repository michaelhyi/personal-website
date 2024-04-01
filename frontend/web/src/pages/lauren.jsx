import lauren from "../assets/lauren.png";
import Center from "../components/Center";
import Container from "../components/Container";
import NewTabLink from "../components/NewTabLink";

export default function Lauren() {
    return (
        <Container absoluteFooter>
            <Center className="flex flex-col items-center gap-4">
                <img
                    src={lauren}
                    width={250}
                    height={250}
                    alt="mimi and lala"
                />
                <p className="text-sm font-light">
                    Happy Birthday Baby!! I hope you had the best birthday ever.
                    I love you!!
                </p>
                <NewTabLink
                    href="https://drive.google.com/drive/folders/1bamXZUe1D-zf6JBgkrGaT1Td3rzLJaCa?usp=sharing"
                    className="underline"
                >
                    Link...
                </NewTabLink>
            </Center>
        </Container>
    );
}
