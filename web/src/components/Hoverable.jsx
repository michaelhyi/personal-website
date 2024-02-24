export default function Hoverable({ children }) {
    return (
        <div className="cursor-pointer duration-300 hover:opacity-50">
            {children}
        </div>
    );
}
