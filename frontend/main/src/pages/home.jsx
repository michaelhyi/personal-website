import "../css/home.css";

import Container from "../components/Container";

export default function Home() {
    return (
        <Container absoluteFooter>
            <div className="center home-section">
                <img
                    src="/michael.png"
                    alt="michael"
                    className="home-headshot"
                />
                <h2 className="home-header">Michael Yi</h2>
                <p className="home-subheader">Software Engineer</p>
                <section className="home-links home-links-text">
                    <a href="/about" className="hoverable">
                        About
                    </a>
                    <p>&nbsp;&nbsp;&#183;&nbsp;&nbsp;</p>
                    <a href="/portfolio" className="hoverable">
                        Portfolio
                    </a>
                    <p>&nbsp;&nbsp;&#183;&nbsp;&nbsp;</p>
                    <a href="/blog" className="hoverable">
                        Blog
                    </a>
                </section>
            </div>
        </Container>
    );
}
