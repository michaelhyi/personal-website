export default function NewTabLink({ children, className, href }) {
    return (
        <a
            href={href}
            rel="noopener noreferrer"
            target="_blank"
            className={className}
        >
            {children}
        </a>
    );
}
