import lauren from "../img/lauren.jpeg";
import Footer from "../components/Footer";

export default function Lauren() {
    return (
        <main>
            <section className="content">
                <div className="center">
                    <img style={{ width: "200px" }} src={lauren} alt="la" />
                </div>
            </section>
            <Footer />
        </main>
    );
}
