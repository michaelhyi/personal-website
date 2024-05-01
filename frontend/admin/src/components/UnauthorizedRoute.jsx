import { useQuery } from "@tanstack/react-query";
import { useNavigate } from "react-router-dom";

import { validateToken } from "../services/auth";
import Loading from "./Loading";

export default function UnauthorizedRoute({ children }) {
    const navigate = useNavigate();
    const { isLoading } = useQuery({
        queryKey: ["isUnauthorized"],
        queryFn: async () => {
            try {
                await validateToken();
                navigate("/blog");
                return true;
            } catch {
                localStorage.removeItem("token");
                return false;
            }
        },
    });

    if (isLoading) return <Loading />;
    return children;
}
