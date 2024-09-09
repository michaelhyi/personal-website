import lauren from "../img/lauren.png";
import Footer from "../components/Footer";

export default function Lauren() {
    return (
        <main>
            <section className="center">
                <img style={{ scale: "0.125" }} src={lauren} alt="la" />
            </section>
            <Footer absolute />
        </main>
    );
}
