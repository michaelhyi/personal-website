import { useCallback } from "react";
import { FaPlus } from "react-icons/fa";
import { FaArrowLeftLong } from "react-icons/fa6";
import { useNavigate } from "react-router-dom";
import Hoverable from "./Hoverable";

export default function BlogHeader() {
    const navigate = useNavigate();

    const logout = useCallback(() => {
        localStorage.removeItem("token");
        navigate("/");
    }, [navigate]);

    return (
        <div className="flex items-center justify-between">
            <button
                type="button"
                onClick={logout}
                className="text-xs text-neutral-300"
            >
                <Hoverable className="flex items-center gap-2">
                    <FaArrowLeftLong /> Logout
                </Hoverable>
            </button>
            <a
                href="/blog/post?mode=create"
                className="focus:outline-none 
                     text-xs 
                   bg-neutral-800
                   text-white 
                     font-semibold 
                     px-3 
                     py-2 
                     rounded-md 
                     shadow-sm"
            >
                <Hoverable className="flex items-center gap-2">
                    <FaPlus />
                    Create Post
                </Hoverable>
            </a>
        </div>
    );
}
