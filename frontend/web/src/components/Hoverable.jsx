export default function Hoverable({ children, className }) {
    return (
        <button
            type="button"
            className={`${className} cursor-pointer duration-300 hover:opacity-50 outline-none`}
        >
            {children}
        </button>
    );
}
