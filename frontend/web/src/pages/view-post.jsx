import { sanitize } from "dompurify";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import BackButton from "../components/BackButton";
import Container from "../components/Container";
import Loading from "../components/Loading";
import NotFound from "../components/NotFound";
import { readPost, readPostImage } from "../services/post";
import format from "../utils/date";

export default function ViewPost() {
    const { id } = useParams();
    const [query, setQuery] = useState({
        data: null,
        loading: true,
        error: false,
    });
    const { data, loading, error } = query;

    useEffect(() => {
        (async () => {
            try {
                const post = await readPost(id);
                setQuery({ data: post, loading: false, error: false });
            } catch (e) {
                setQuery({ data: null, loading: false, error: true });
            }
        })();
    }, []);

    if (loading) return <Loading />;
    if (error) return <NotFound />;

    return (
        <Container>
            <BackButton href="/blog" text="Blog" />
            <h1 className="mt-10 text-3xl font-bold">{data.title}</h1>
            <p className="mt-4 text-xs text-neutral-400">{format(data.date)}</p>
            <img
                src={readPostImage(id)}
                alt={data.title}
                className="w-full mt-6"
            />
            <article
                className="text-[15px] mt-8"
                // eslint-disable-next-line react/no-danger -- html data is sanitized
                dangerouslySetInnerHTML={{
                    __html: sanitize(data.content),
                }}
            />
        </Container>
    );
}
