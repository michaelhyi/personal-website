import { useEffect, useState } from "react";
import "../css/blog.css";
import { getAllPosts } from "../js/post-service";
import Loading from "./loading";
import NotFound from "./not-found";
import Footer from "../components/Footer";

export default function Blog() {
    const [query, setQuery] = useState({
        data: null,
        error: false,
        loading: true,
    });

    const { data, error, loading } = query;

    useEffect(() => {
        (async () => {
            try {
                const posts = await getAllPosts();
                setQuery({
                    data: posts,
                    loading: false,
                    error: false,
                });
            } catch (e) {
                setQuery({
                    data: null,
                    loading: false,
                    error: true,
                });
            }
        })();
    }, []);

    if (error) return <NotFound />;
    if (loading) return <Loading />;

    return (
        <main>
            <section className="content">
                <a href="/" className="back-arrow">
                    &#8592;
                </a>
                <section className="blog-section">
                    {data.map(({ id, title }) => (
                        <a
                            key={id}
                            className="blog-post-anchor"
                            href={`/blog/${id}`}
                        >
                            {title}
                            <span className="blog-post-arrow">&#8599;</span>
                        </a>
                    ))}
                </section>
            </section>
            <Footer absolute />
        </main>
    );
}
