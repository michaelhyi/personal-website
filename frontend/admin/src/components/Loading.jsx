import Center from "./Center";
import Container from "./Container";
import Spinner from "./Spinner";

export default function Loading() {
    return (
        <Container absoluteFooter>
            <Center>
                <Spinner />
            </Center>
        </Container>
    );
}
