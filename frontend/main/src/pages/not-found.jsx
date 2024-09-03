import "../css/not-found.css";

import Container from "../components/Container";

export default function NotFound() {
    return (
        <Container absoluteFooter>
            <div className="center">
                <p className="not-found-text">Not Found</p>
            </div>
        </Container>
    );
}
