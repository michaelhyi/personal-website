import "../css/footer.css";

export default function Footer({ absolute }) {
    return (
        <footer
            className={`
                footer-wrapper
                  ${absolute ? "footer-absolute" : "footer-relative"}
                `}
        >
            <p>&copy; 2023 Michael Yi, All Rights Reserved.</p>
        </footer>
    );
}
