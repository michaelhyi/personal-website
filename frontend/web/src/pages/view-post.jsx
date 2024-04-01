import { format } from "date-fns";
import { sanitize } from "dompurify";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import BackButton from "../components/BackButton";
import Container from "../components/Container";
import Loading from "../components/Loading";
import NotFound from "../components/NotFound";

import { readPost, readPostImage } from "../services/post";

export default function ViewPost() {
    const { id } = useParams();
    const [data, setData] = useState(null);
    const [notFound, setNotFound] = useState(false);

    useEffect(() => {
        (async () => {
            try {
                setData(await readPost(id));
            } catch {
                setNotFound(true);
            }
        })();
    }, [id]);

    if (notFound) return <NotFound />;
    if (!data) return <Loading />;

    return (
        <Container>
            <BackButton href="/blog" text="Blog" />
            <h1 className="mt-10 text-3xl font-bold">{data.title}</h1>
            <p className="mt-4 text-xs text-neutral-400">
                {format(new Date(data.date), "PPP")}
            </p>
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
