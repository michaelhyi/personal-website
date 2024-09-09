import "../css/back-button.css";

import FaArrowLeftLong from "./Icons/FaArrowLeftLong";

export default function BackButton({ href, text }) {
    return (
        <a href={href} className="hoverable back-button-anchor">
            <FaArrowLeftLong /> Back to {text}
        </a>
    );
}
