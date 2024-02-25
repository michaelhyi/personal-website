import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { validateToken } from "../services/auth";
import Loading from "./Loading";

export default function UnauthorizedRoute({ children }) {
    const navigate = useNavigate();
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        (async () => {
            const token = localStorage.getItem("token");

            if (!token) {
                setLoading(false);
            } else {
                try {
                    await validateToken(token);
                    navigate("/blog");
                } catch {
                    localStorage.removeItem("token");
                    setLoading(false);
                }
            }
        })();
    }, [navigate]);

    if (loading) return <Loading />;
    return children;
}
