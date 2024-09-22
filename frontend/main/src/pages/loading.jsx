import Footer from "../components/Footer";
import Spinner from "../components/Spinner";

export default function Loading() {
        return (
                <main>
                        <section>
                                <div className="center">
                                        <Spinner />
                                </div>
                        </section>
                        <Footer />
                </main>
        );
}
