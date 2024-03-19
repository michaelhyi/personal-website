import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { validateToken } from "../services/auth";
import Loading from "./Loading";

export default function AuthorizedRoute({ children }) {
    const navigate = useNavigate();
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        (async () => {
            try {
                await validateToken();
                setLoading(false);
            } catch {
                localStorage.removeItem("token");
                navigate("/");
            }
        })();
    }, [navigate]);

    if (loading) return <Loading />;
    return children;
}
