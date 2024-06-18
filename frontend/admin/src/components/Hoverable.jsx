export default function Hoverable({ children, className, onClick }) {
    return (
        <button
            type="button"
            className={`${className} cursor-pointer duration-300 hover:opacity-50 outline-none`}
            onClick={onClick}
        >
            {children}
        </button>
    );
}
