import { useEffect, useState } from "react";
import AuthorizedRoute from "../components/AuthorizedRoute";
import Footer from "../components/Footer";
import FaPlus from "../components/Icons/FaPlus";
import "../css/blog.css";
import logout from "../js/auth-util";
import { getAllPosts } from "../js/post-service";
import Loading from "./loading";

export default function Blog() {
    const [query, setQuery] = useState({
        data: null,
        loading: true,
        error: false,
    });
    const { data, loading } = query;

    useEffect(() => {
        (async () => {
            try {
                const posts = await getAllPosts();
                setQuery({ data: posts, loading: false, error: false });
            } catch (e) {
                setQuery({ data: null, loading: false, error: true });
            }
        })();
    }, []);

    if (loading) return <Loading />;

    return (
        <AuthorizedRoute>
            <main>
                <div className="content">
                    <header className="header-wrapper">
                        <button
                            type="button"
                            onClick={logout}
                            className="header-logout-btn"
                        >
                            <p className="back-arrow">
                                &#8592;
                            </p>
                        </button>
                        <a aria-label="Create post" href="/blog/post">
                            <FaPlus />
                        </a>
                    </header>
                    <section className="blog-body">
                        {data &&
                            data.map((post) => (
                                <div key={post.id} className="blog-post-card">
                                    <a
                                        className="blog-post-title"
                                        href={`/blog/post?id=${post.id}`}
                                    >
                                        {post.title}
                                    </a>
                                </div>
                            ))}
                    </section>
                </div>
                <Footer />
            </main>
        </AuthorizedRoute>
    );
}
