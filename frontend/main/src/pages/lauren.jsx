import lauren from "../img/lauren.png";

import Container from "../components/Container";

export default function Lauren() {
    return (
        <Container absoluteFooter>
            <section className="center">
                <img style={{ scale: "0.125" }} src={lauren} alt="la" />
            </section>
        </Container>
    );
}
