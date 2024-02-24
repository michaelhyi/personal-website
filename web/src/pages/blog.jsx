import { useEffect, useState } from "react";
import { FiArrowUpRight } from "react-icons/fi";

import BackButton from "../components/BackButton";
import Container from "../components/Container";
import Hoverable from "../components/Hoverable";
import Loading from "../components/Loading";
import NotFound from "../components/NotFound";

import { readAllPosts } from "../services/post";

export default function Blog() {
    const [data, setData] = useState(null);
    const [notFound, setNotFound] = useState(false);

    useEffect(() => {
        (async () => {
            try {
                setData(await readAllPosts());
            } catch {
                setNotFound(true);
            }
        })();
    }, []);

    if (notFound) return <NotFound />;
    if (!data) return <Loading />;

    return (
        <Container absoluteFooter>
            <BackButton href="/" text="Home" />
            <div className="mt-10 flex flex-col gap-2">
                {data.map((post) => (
                    <Hoverable key={post.id}>
                        <a
                            className="flex text-sm font-medium"
                            href={`/blog/${post.id}`}
                        >
                            {post.title} <FiArrowUpRight />
                        </a>
                    </Hoverable>
                ))}
            </div>
        </Container>
    );
}
