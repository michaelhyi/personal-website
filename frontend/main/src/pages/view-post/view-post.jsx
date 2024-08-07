import "./view-post.css";

import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { sanitize } from "dompurify";

import { BackButton, Container, Loading, NotFound } from "../../components";
import { getPost, getPostImage } from "../../services/post";
import format from "../../utils/date";

export default function ViewPost() {
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
                setQuery({ data: post, loading: false, error: false });
            } catch (e) {
                setQuery({ data: null, loading: false, error: true });
            }
        })();
    }, []);

    if (error) return <NotFound />;
    if (loading) return <Loading />;

    return (
        <Container>
            <BackButton href="/blog" text="Blog" />
            <h1 className="post-title">{data.title}</h1>
            <p className="post-date">{format(data.date)}</p>
            <img src={getPostImage(id)} alt={data.title} className="post-img" />
            <article
                className="post-article"
                // eslint-disable-next-line react/no-danger -- html data is sanitized
                dangerouslySetInnerHTML={{
                    __html: sanitize(data.content),
                }}
            />
        </Container>
    );
}
