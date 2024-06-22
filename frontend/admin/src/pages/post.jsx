import { useCallback, useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";

import AuthorizedRoute from "../components/AuthorizedRoute";
import BackButton from "../components/BackButton";
import Container from "../components/Container";
import Dropzone from "../components/Dropzone";
import Editor from "../components/Editor";
import Loading from "../components/Loading";
import NotFound from "../components/NotFound";
import Spinner from "../components/Spinner";

import useEditor from "../hooks/useEditor";
import { createPost, readPost, updatePost } from "../services/post";
import validateForm from "../utils/validateForm";

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
    const [showImage, setShowImage] = useState(params.get("mode") === "edit");
    const editor = useEditor(data && `<h1>${data.title}</h1>${data.content}`);

    const handleSubmit = useCallback(async () => {
        setSubmitting(true);

        try {
            const text = editor.getHTML();
            validateForm(text, image, showImage);

            let id = params.get("id") || null;
            const formData = new FormData();
            formData.append("text", text);
            formData.append("image", image || null);

            if (params.get("mode") === "edit") {
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
            if (params.get("mode") === "edit") {
                try {
                    const post = await readPost(params.get("id"));
                    setQuery({ data: post, loading: false, error: false });
                } catch (e) {
                    setQuery({ data: null, loading: false, error: true });
                }
            } else {
                setQuery({ data: null, loading: false, error: false });
            }
        })();
    }, []);

    if (loading) return <Loading />;
    if (notFound) return <NotFound />;

    return (
        <AuthorizedRoute>
            <Container>
                <BackButton href="/blog" text="Blog" />
                <Editor editor={editor} disabled={submitting} />
                <div className="mt-4" />
                <Dropzone
                    id={params.get("id")}
                    showImage={showImage}
                    setShowImage={setShowImage}
                    title={data && data.title}
                    submitting={submitting}
                    image={image}
                    setImage={setImage}
                />
                <p className="ml-auto min-h-4 mt-4 text-xs text-red-300 font-light">
                    {error || ""}
                </p>
                <button
                    type="submit"
                    onClick={handleSubmit}
                    className="mt-3
                     ml-auto
                     text-sm
                     flex
                     items-center 
                     gap-3 
                     bg-neutral-800
                     text-white 
                     font-semibold 
                     px-6
                     py-2
                     rounded-md 
                     shadow-sm
                     duration-500 
                     hover:opacity-50"
                >
                    {submitting ? <Spinner /> : "Submit"}
                </button>
            </Container>
        </AuthorizedRoute>
    );
}
