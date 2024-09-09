import Footer from "../components/Footer";
import "../css/home.css";

export default function Home() {
    return (
        <main>
            <section className="content">
                <div className="center home-section">
                    <img
                        src="/michael.png"
                        alt="michael"
                        className="home-headshot"
                    />
                    <h2 className="home-header">Michael Yi</h2>
                    <p className="home-subheader">Software Engineer</p>
                    <section className="home-links home-links-text">
                        <a href="/about">About</a>
                        <p>&nbsp;&nbsp;&#183;&nbsp;&nbsp;</p>
                        <a href="/portfolio">Portfolio</a>
                        <p>&nbsp;&nbsp;&#183;&nbsp;&nbsp;</p>
                        <a href="/blog">Blog</a>
                    </section>
                </div>
            </section>
            <Footer absolute />
        </main>
    );
}
