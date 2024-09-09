import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Loading from "../pages/loading";
import { validateToken } from "../services/auth";

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
    }, []);

    if (loading) return <Loading />;
    return children;
}
