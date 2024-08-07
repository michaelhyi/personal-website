import "./blog.css";

import { useEffect, useState } from "react";

import FiArrowUpRight from "../../components/Icons/FiArrowUpRight";

import {
    BackButton,
    Container,
    Hoverable,
    Loading,
    NotFound,
} from "../../components";
import { getAllPosts } from "../../services/post";

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
                setQuery({ data: posts, loading: false, error: false });
            } catch (e) {
                setQuery({ data: null, loading: false, error: true });
            }
        })();
    }, []);

    if (error) return <NotFound />;
    if (loading) return <Loading />;

    return (
        <Container absoluteFooter>
            <BackButton href="/" text="Home" />
            <section className="blog-section">
                {data.map(({ id, title }) => (
                    <Hoverable className="blog-post" key={id}>
                        <a className="blog-post-anchor" href={`/blog/${id}`}>
                            {title}
                            <span className="blog-post-arrow">
                                <FiArrowUpRight />
                            </span>
                        </a>
                    </Hoverable>
                ))}
            </section>
        </Container>
    );
}
