import "./home.css";

import { useCallback, useState } from "react";
import { useNavigate } from "react-router-dom";

import { Center, Container, UnauthorizedRoute } from "../../components";
import { login } from "../../services/auth";

export default function Home() {
    const navigate = useNavigate();
    const [error, setError] = useState("");

    const handleSubmit = useCallback(
        async (e) => {
            e.preventDefault();

            try {
                await login(e.target[0].value);
                navigate("/blog");
            } catch (err) {
                setError(err.message);
            }
        },
        [navigate, setError],
    );

    return (
        <UnauthorizedRoute>
            <Container absoluteFooter>
                <Center className="home-content">
                    <form onSubmit={handleSubmit}>
                        <input
                            type="password"
                            className="home-pw-input"
                            placeholder="Password"
                        />
                    </form>
                    <p className="home-error-message">{error || ""}</p>
                </Center>
            </Container>
        </UnauthorizedRoute>
    );
}
