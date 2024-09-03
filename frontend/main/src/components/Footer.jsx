import "../css/footer.css";

import AiGithub from "./AiGithub";
import AiLinkedin from "./AiLinkedin";

export default function Footer({ absolute }) {
    return (
        <footer
            className={`footer-wrapper ${absolute ? "footer-absolute" : "footer-relative"}`}
        >
            <section className="footer-links">
                <a
                    aria-label="Github"
                    href="https://github.com/michaelhyi/"
                    className="hoverable"
                    rel="noopener noreferrer"
                    target="_blank"
                >
                    <AiGithub />
                </a>
                <a
                    aria-label="linkedin"
                    href="https://www.linkedin.com/in/michaelhyi/"
                    className="hoverable"
                    rel="noopener noreferrer"
                    target="_blank"
                >
                    <AiLinkedin />
                </a>
            </section>
            <p>&copy; 2023 Michael Yi, All Rights Reserved.</p>
        </footer>
    );
}
