import { useEffect, useState } from "react";
import Loading from "../pages/loading";
import { validateToken } from "../js/auth-service";
import logout from "../js/auth-util";

export default function AuthorizedRoute({ children }) {
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        (async () => {
            try {
                await validateToken();
                setLoading(false);
            } catch {
                logout();
            }
        })();
    }, []);

    if (loading) return <Loading />;
    return children;
}
