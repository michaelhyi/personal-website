import Spinner from "../components/Spinner";
import Footer from "../components/Footer";

export default function Loading() {
    return (
        <main>
            <div className="content">
                <section className="center">
                    <Spinner />
                </section>
                <Footer />
            </div>
        </main>
    );
}
