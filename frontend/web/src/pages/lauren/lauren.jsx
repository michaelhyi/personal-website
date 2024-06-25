import "./lauren.css";

import lauren from "../../assets/lauren.png";
import { Center, Container, NewTabLink } from "../../components";

export default function Lauren() {
    return (
        <Container absoluteFooter>
            <Center className="lauren-section">
                <img
                    src={lauren}
                    width={250}
                    height={250}
                    alt="mimi and lala"
                />
                <p className="lauren-text">
                    Happy Birthday Baby!! I hope you had the best birthday ever.
                    I love you!!
                </p>
                <NewTabLink
                    href="https://drive.google.com/drive/folders/1bamXZUe1D-zf6JBgkrGaT1Td3rzLJaCa?usp=sharing"
                    className="lauren-link"
                >
                    Link...
                </NewTabLink>
            </Center>
        </Container>
    );
}
