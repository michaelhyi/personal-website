import "./BlogHeader.css";

import { useCallback } from "react";
import { useNavigate } from "react-router-dom";

import FaArrowLeftLong from "../Icons/FaArrowLeftLong";
import FaPlus from "../Icons/FaPlus";
import Hoverable from "../Hoverable/Hoverable";

export default function BlogHeader() {
    const navigate = useNavigate();

    const logout = useCallback(() => {
        localStorage.removeItem("token");
        navigate("/");
    }, [navigate]);

    return (
        <header className="header-wrapper">
            <button
                type="button"
                onClick={logout}
                className="header-logout-btn"
            >
                <Hoverable className="header-logout-btn-content">
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
