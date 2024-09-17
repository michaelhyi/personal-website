import { useCallback, useState } from "react";
import { useNavigate } from "react-router-dom";
import Footer from "../components/Footer";
import UnauthorizedRoute from "../components/UnauthorizedRoute";
import "../css/home.css";
import { login } from "../js/auth-service";

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
            <main>
                <div className="content">
                    <section className="center home-content">
                        <form onSubmit={handleSubmit}>
                            <input
                                type="password"
                                className="home-pw-input"
                                placeholder="Password"
                            />
                        </form>
                        <p className="home-error-message">{error || ""}</p>
                    </section>
                </div>
                <Footer />
            </main>
        </UnauthorizedRoute>
    );
}
