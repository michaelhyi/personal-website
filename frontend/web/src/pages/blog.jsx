import { useEffect, useState } from "react";

import FiArrowUpRight from "../assets/icons/FiArrowUpRight";
import BackButton from "../components/BackButton";
import Container from "../components/Container";
import Hoverable from "../components/Hoverable";
import Loading from "../components/Loading";
import NotFound from "../components/NotFound";
import { readAllPosts } from "../services/post";

export default function Blog() {
    const [query, setQuery] = useState({
        data: null,
        loading: true,
        error: false,
    });

    const { data, loading, error } = query;

    useEffect(() => {
        (async () => {
            try {
                const posts = await readAllPosts();
                setQuery({ data: posts, loading: false, error: false });
            } catch (e) {
                setQuery({ data: null, loading: false, error: true });
            }
        })();
    }, []);

    if (loading) return <Loading />;
    if (error) return <NotFound />;

    return (
        <Container absoluteFooter>
            <BackButton href="/" text="Home" />
            <section className="mt-10 flex flex-col gap-2">
                {data.map((post) => (
                    <Hoverable className="text-left" key={post.id}>
                        <a
                            className="text-sm font-medium"
                            href={`/blog/${post.id}`}
                        >
                            {post.title}
                            <span className="inline-block">
                                <FiArrowUpRight />
                            </span>
                        </a>
                    </Hoverable>
                ))}
            </section>
        </Container>
    );
}
