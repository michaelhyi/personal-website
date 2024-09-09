import Container from "../components/Container";
import Spinner from "../components/Spinner";

export default function Loading() {
    return (
        <Container absoluteFooter>
            <section className="center">
                <Spinner />
            </section>
        </Container>
    );
}
