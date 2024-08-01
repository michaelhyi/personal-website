import "./BackButton.css";

import FaArrowLeftLong from "../Icons/FaArrowLeftLong";
import Hoverable from "../Hoverable/Hoverable";

export default function BackButton({ href, text }) {
    return (
        <Hoverable>
            <a href={href} className="back-button-anchor">
                <FaArrowLeftLong /> Back to {text}
            </a>
        </Hoverable>
    );
}
