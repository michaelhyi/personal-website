import "./Hoverable.css";

export default function Hoverable({ children, className, onClick }) {
    return (
        <button
            type="button"
            onClick={onClick}
            className={`${className} hoverable-wrapper`}
        >
            {children}
        </button>
    );
}
