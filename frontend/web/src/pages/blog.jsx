import { useQuery } from "@tanstack/react-query";
import { FiArrowUpRight } from "react-icons/fi";

import BackButton from "../components/BackButton";
import Container from "../components/Container";
import Hoverable from "../components/Hoverable";
import Loading from "../components/Loading";
import NotFound from "../components/NotFound";

import { readAllPosts } from "../services/post";

export default function Blog() {
    const { data, isLoading, isError } = useQuery({
        queryKey: ["readAllPosts"],
        queryFn: readAllPosts,
    });

    if (isLoading) return <Loading />;
    if (isError) return <NotFound />;

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
