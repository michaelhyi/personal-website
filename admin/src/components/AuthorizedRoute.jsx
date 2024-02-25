import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { validateToken } from "../services/auth";
import Loading from "./Loading";

export default function AuthorizedRoute({ children }) {
    const navigate = useNavigate();
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        (async () => {
            const token = localStorage.getItem("token");

            if (!token) {
                navigate("/");
            } else {
                try {
                    await validateToken(token);
                    setLoading(false);
                } catch {
                    localStorage.removeItem("token");
                    navigate("/");
                }
            }
        })();
    }, [navigate]);

    if (loading) return <Loading />;
    return children;
}
