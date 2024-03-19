import Center from "./Center";
import Container from "./Container";
import Spinner from "./Spinner";

export default function Loading() {
    return (
        <Container absoluteFooter>
            <Center>
                <div className="text-neutral-300">
                    <Spinner />
                </div>
            </Center>
        </Container>
    );
}
