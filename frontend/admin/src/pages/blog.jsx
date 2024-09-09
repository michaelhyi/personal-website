import "../css/blog.css";

import { useEffect, useState } from "react";

import AuthorizedRoute from "../components/AuthorizedRoute";
import BlogHeader from "../components/BlogHeader";
import Container from "../components/Container";
import Loading from "./loading";

import { getAllPosts } from "../services/post";

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
            <Container absoluteFooter>
                <BlogHeader />
                <section className="blog-body">
                    {data &&
                        data.map((post) => (
                            <div key={post.id} className="blog-post-card">
                                <a
                                    className="hoverable blog-post-title"
                                    href={`/blog/post?id=${post.id}`}
                                >
                                    {post.title}
                                </a>
                            </div>
                        ))}
                </section>
            </Container>
        </AuthorizedRoute>
    );
}
