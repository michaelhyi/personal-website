import "../css/read-post.css";

import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { sanitize } from "dompurify";

import BackButton from "../components/BackButton";
import Container from "../components/Container";
import Loading from "./loading";
import NotFound from "./not-found";

import { getPost, getPostImage } from "../services/post";
import format from "../util/date";

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
                const image = await getPostImage(id);
                setQuery({
                    data: { post, image },
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
        <Container>
            <BackButton href="/blog" />
            <h1 className="post-title">{data.post.title}</h1>
            <p className="post-date">{format(data.post.date)}</p>
            <img
                src={`data:image/png;base64,${data.image}`}
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
        </Container>
    );
}
