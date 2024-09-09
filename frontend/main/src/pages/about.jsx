import "../css/about.css";
import Footer from "../components/Footer";

export default function About() {
    return (
        <main>
            <section className="content">
                <div className="center">
                    <section className="about-details">
                        <a href="/" className="back-arrow">
                            &#8592;
                        </a>
                        <p>
                            Michael Yi is a software engineer currently
                            interning at T-Mobile. He&apos;s a junior studying
                            Computer Science at Georgia Tech, and he previously
                            worked at Ardent Labs and MegaEvolution.
                        </p>
                        <p>
                            Michael loves building. When he&apos;s not creating
                            software, he loves watching cinema and playing
                            piano.
                        </p>
                        <p>
                            Reach him at&nbsp;
                            <a
                                href="mailto:contact@michael-yi.com"
                                className="about-contact"
                            >
                                contact@michael-yi.com
                            </a>
                            .
                        </p>
                    </section>
                </div>
            </section>
            <Footer absolute />
        </main>
    );
}
