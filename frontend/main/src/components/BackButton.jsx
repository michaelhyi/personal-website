import FaArrowLeftLong from "./FaArrowLeftLong";

export default function BackButton({ href }) {
    return (
        <a aria-label="back" href={href} className="hoverable">
            <FaArrowLeftLong />
        </a>
    );
}
