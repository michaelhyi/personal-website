import "./NotFound.css";

import Center from "../Center/Center";
import Container from "../Container/Container";

export default function NotFound() {
    return (
        <Container absoluteFooter>
            <Center>
                <p className="not-found-text">Not Found</p>
            </Center>
        </Container>
    );
}
