import { useQuery } from "@tanstack/react-query";
import { useNavigate } from "react-router-dom";
import { validateToken } from "../services/auth";
import Loading from "./Loading";

export default function AuthorizedRoute({ children }) {
    const navigate = useNavigate();
    const { isLoading } = useQuery({
        queryKey: ["isAuthorized"],
        queryFn: async () => {
            try {
                await validateToken();
                return true;
            } catch {
                localStorage.removeItem("token");
                navigate("/");
                return false;
            }
        },
    });

    if (isLoading) return <Loading />;
    return children;
}
