import "../css/blog.css";

import { useEffect, useState } from "react";

import BackButton from "../components/BackButton";
import Container from "../components/Container";
import FiArrowUpRight from "../components/FiArrowUpRight";
import Loading from "./loading";
import NotFound from "./not-found";

import { getAllPosts } from "../services/post";

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
        <Container absoluteFooter>
            <BackButton href="/" />
            <section className="blog-section">
                {data.map(({ id, title }) => (
                    <a
                        key={id}
                        className="blog-post-anchor hoverable"
                        href={`/blog/${id}`}
                    >
                        {title}
                        <span className="blog-post-arrow">
                            <FiArrowUpRight />
                        </span>
                    </a>
                ))}
            </section>
        </Container>
    );
}
