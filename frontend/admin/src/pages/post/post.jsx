import "./post.css";

import { useCallback, useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";

import {
    AuthorizedRoute,
    BackButton,
    Container,
    Dropzone,
    Editor,
    Loading,
    NotFound,
    Spinner,
} from "../../components";
import useEditor from "../../hooks/useEditor";
import { createPost, readPost, updatePost } from "../../services/post";
import validateForm from "../../utils/validateForm";

export default function Post() {
    const [params] = useSearchParams();
    const [query, setQuery] = useState({
        data: null,
        loading: true,
        error: false,
    });
    const { data, loading, error: notFound } = query;

    const [image, setImage] = useState(null);
    const [error, setError] = useState(null);
    const [submitting, setSubmitting] = useState(false);
    const [showImage, setShowImage] = useState(params.get("id") !== null);
    const editor = useEditor(data && `<h1>${data.title}</h1>${data.content}`);

    const handleSubmit = useCallback(async () => {
        setSubmitting(true);

        try {
            const text = editor.getHTML();
            validateForm(text, image, showImage);

            let id = params.get("id");
            const formData = new FormData();
            formData.append("text", text);
            formData.append("image", image || null);

            if (id) {
                await updatePost(id, formData);
            } else {
                id = await createPost(formData);
            }

            window.location.href = `${process.env.REACT_APP_WEB_URL}/blog/${id}`;
        } catch ({ message }) {
            setError(message);
        } finally {
            setSubmitting(false);
        }
    }, [setSubmitting, editor, image]);

    useEffect(() => {
        (async () => {
            const id = params.get("id");

            if (id) {
                try {
                    const post = await readPost(id);
                    setQuery({ data: post, loading: false, error: false });
                } catch (e) {
                    setQuery({ data: null, loading: false, error: true });
                }
            } else {
                setQuery({ data: null, loading: false, error: false });
            }
        })();
    }, [editor]);

    if (loading) return <Loading />;
    if (notFound) return <NotFound />;

    return (
        <AuthorizedRoute>
            <Container footer={false}>
                <BackButton href="/blog" text="Blog" />
                <Editor editor={editor} disabled={submitting} />
                <div
                    style={{
                        marginTop: "16px",
                    }}
                />
                <Dropzone
                    id={params.get("id")}
                    showImage={showImage}
                    setShowImage={setShowImage}
                    title={data && data.title}
                    submitting={submitting}
                    image={image}
                    setImage={setImage}
                />
                <p className="post-error-message">{error || ""}</p>
                <button
                    type="submit"
                    onClick={handleSubmit}
                    className="post-submit-btn"
                >
                    {submitting ? <Spinner /> : "Submit"}
                </button>
            </Container>
        </AuthorizedRoute>
    );
}
