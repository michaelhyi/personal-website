import "./Hoverable.css";

export default function Hoverable({ children, className }) {
    return (
        <button type="button" className={`${className} hoverable-wrapper`}>
            {children}
        </button>
    );
}
