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
        error: false,
        loading: true,
    });

    const { data, error, loading } = query;

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

    if (error) return <NotFound />;
    if (loading) return <Loading />;

    return (
        <Container absoluteFooter>
            <BackButton href="/" text="Home" />
            <section className="mt-10 flex flex-col gap-2">
                {data.map(({ id, title }) => (
                    <Hoverable className="text-left" key={id}>
                        <a className="text-sm font-medium" href={`/blog/${id}`}>
                            {title}
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
