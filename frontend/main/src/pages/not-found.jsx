import Footer from "../components/Footer";
import "../css/not-found.css";

export default function NotFound() {
    return (
        <main>
            <section className="center">
                <p className="not-found-text">Not Found</p>
            </section>
            <Footer absolute />
        </main>
    );
}
