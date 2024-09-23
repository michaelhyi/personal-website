import { useEffect, useState } from "react";
import Loading from "../pages/loading";
import { validateToken } from "../js/auth-service";

export default function UnauthorizedRoute({ children }) {
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        (async () => {
            try {
                await validateToken();
                window.location.href = "/blog";
            } catch {
                localStorage.removeItem("token");
                setLoading(false);
            }
        })();
    }, []);

    if (loading) return <Loading />;
    return children;
}
