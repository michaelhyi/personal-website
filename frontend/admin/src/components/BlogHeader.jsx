import { useCallback } from "react";
import { useNavigate } from "react-router-dom";
import FaArrowLeftLong from "../assets/icons/FaArrowLeftLong";
import FaPlus from "../assets/icons/FaPlus";
import Hoverable from "./Hoverable";

export default function BlogHeader() {
    const navigate = useNavigate();

    const logout = useCallback(() => {
        localStorage.removeItem("token");
        navigate("/");
    }, [navigate]);

    return (
        <header className="flex items-center justify-between">
            <button
                type="button"
                onClick={logout}
                className="text-xs text-neutral-300"
            >
                <Hoverable className="flex items-center gap-2">
                    <FaArrowLeftLong /> Logout
                </Hoverable>
            </button>
            <Hoverable>
                <a aria-label="Create post" href="/blog/post?mode=create">
                    <FaPlus />
                </a>
            </Hoverable>
        </header>
    );
}
