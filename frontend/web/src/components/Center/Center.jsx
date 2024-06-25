import "./Center.css";

export default function Center({ children, className }) {
    return (
        <section className={`${className} center-wrapper`}>{children}</section>
    );
}
