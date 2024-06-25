import "./Footer.css";

import Hoverable from "../Hoverable/Hoverable";
import NewTabLink from "../NewTabLink/NewTabLink";

import FOOTER_LINKS from "../../utils/constants/footer-links"

export default function Footer({ absolute }) {
    return (
        <footer
            className={`footer-wrapper ${absolute ? "footer-absolute" : "footer-relative"}`}
        >
            <section className="footer-links">
                {FOOTER_LINKS.map(({ href, icon: Icon }) => (
                    <Hoverable key={href}>
                        <NewTabLink href={href}>
                            <Icon />
                        </NewTabLink>
                    </Hoverable>
                ))}
            </section>
            <p>&copy; 2023 Michael Yi, All Rights Reserved.</p>
        </footer>
    );
}
