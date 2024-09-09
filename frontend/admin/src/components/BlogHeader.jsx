import "../css/blog-header.css";

import { useCallback } from "react";
import { useNavigate } from "react-router-dom";

import FaArrowLeftLong from "./Icons/FaArrowLeftLong";
import FaPlus from "./Icons/FaPlus";

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
                className="hoverable header-logout-btn"
            >
                <div className="header-logout-btn-content">
                    <FaArrowLeftLong /> Logout
                </div>
            </button>
            <a aria-label="Create post" href="/blog/post" className="hoverable">
                <FaPlus />
            </a>
        </header>
    );
}
