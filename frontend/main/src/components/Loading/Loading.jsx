import Center from "../Center/Center";
import Container from "../Container/Container";
import Spinner from "../Spinner/Spinner";

export default function Loading() {
    return (
        <Container absoluteFooter>
            <Center>
                <Spinner />
            </Center>
        </Container>
    );
}
