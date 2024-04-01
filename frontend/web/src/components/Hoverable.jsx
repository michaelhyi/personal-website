export default function Hoverable({ children }) {
    return (
        <button
            type="button"
            className="cursor-pointer duration-300 hover:opacity-50"
        >
            {children}
        </button>
    );
}
