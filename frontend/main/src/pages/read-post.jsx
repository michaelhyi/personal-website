import "../css/read-post.css";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { sanitize } from "dompurify";
import Loading from "./loading";
import NotFound from "./not-found";
import { getPost } from "../js/post-service";
import format from "../js/date-util";
import Footer from "../components/Footer";

export default function ReadPost() {
    const { id } = useParams();
    const [query, setQuery] = useState({
        data: null,
        error: false,
        loading: true,
    });
    const { data, error, loading } = query;

    useEffect(() => {
        (async () => {
            try {
                const post = await getPost(id);

                setQuery({
                    data: { post },
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
                <a href="/blog" className="back-arrow">
                    &#8592;
                </a>

                <h1 className="post-title">{data.post.title}</h1>
                <p className="post-date">{format(data.post.date)}</p>
                <img
                    src={`data:image/png;base64,${data.post.image}`}
                    alt={data.post.title}
                    className="post-img"
                />
                <article
                    className="post-article"
                    // eslint-disable-next-line react/no-danger -- html data is sanitized
                    dangerouslySetInnerHTML={{
                        __html: sanitize(data.post.content),
                    }}
                />
            </section>
            <Footer />
        </main>
    );
}
