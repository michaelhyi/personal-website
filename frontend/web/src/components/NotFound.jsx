import Center from "./Center";
import Container from "./Container";

export default function NotFound() {
    return (
        <Container absoluteFooter>
            <Center>
                <p className="text-[10px] text-neutral-300">Not Found</p>
            </Center>
        </Container>
    );
}
