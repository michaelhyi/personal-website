import FaArrowLeftLong from "../assets/icons/FaArrowLeftLong";
import Hoverable from "./Hoverable";

export default function BackButton({ href, text }) {
    return (
        <Hoverable>
            <a
                href={href}
                className="flex items-center gap-2 text-xs text-neutral-400"
            >
                <FaArrowLeftLong /> Back to {text}
            </a>
        </Hoverable>
    );
}
