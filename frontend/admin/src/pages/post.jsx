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
import Toast from "../components/Toast";

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
    const { data, loading, error } = query;

    const [image, setImage] = useState(null);
    const [submitting, setSubmitting] = useState(false);
    const [showImage, setShowImage] = useState(params.get("mode") === "edit");
    const [toast, setToast] = useState({
        message: "",
        visible: false,
        animation: "animate-fadeIn",
        success: true,
    });

    const editor = useEditor(data && `<h1>${data.title}</h1>${data.content}`);

    const handleSubmit = useCallback(async () => {
        setSubmitting(true);

        try {
            const text = editor.getHTML();
            validateForm(text, image, showImage);

            const formData = new FormData();
            formData.append("text", text);
            formData.append("image", image || null);

            if (params.get("mode") === "edit") {
                await updatePost(params.get("id"), formData);
            } else {
                await createPost(formData);
            }

            setToast({
                message: `Post successfully ${
                    params.get("mode") === "create" ? "published" : "updated"
                }!`,
                visible: true,
                animation: "animate-fadeIn",
                success: true,
            });

            setTimeout(() => {
                setToast({
                    visible: true,
                    message: `Post successfully ${
                        params.get("mode") === "create"
                            ? "published"
                            : "updated"
                    }!`,
                    animation: "animate-fadeOut",
                    success: true,
                });
            }, 3000);

            setTimeout(() => {
                setToast({
                    message: "",
                    visible: false,
                    animation: "animate-fadeIn",
                    success: true,
                });
            }, 4000);
        } catch (e) {
            setToast({
                message: e.response ? e.response.data : e.message,
                visible: true,
                animation: "animate-fadeIn",
                success: false,
            });

            setTimeout(() => {
                setToast({
                    visible: true,
                    message: e.response ? e.response.data : e.message,
                    animation: "animate-fadeOut",
                    success: false,
                });
            }, 3000);

            setTimeout(() => {
                setToast({
                    message: "",
                    visible: false,
                    animation: "animate-fadeIn",
                    success: true,
                });
            }, 4000);
        } finally {
            setSubmitting(false);
        }
    }, []);

    useEffect(() => {
        (async () => {
            if (params.get("mode") === "edit") {
                try {
                    const post = readPost(params.get("id"));
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
    if (error) return <NotFound />;

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
                <button
                    type="submit"
                    onClick={handleSubmit}
                    className="mt-12
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
                {toast.visible && (
                    <Toast
                        animation={toast.animation}
                        message={toast.message}
                        success={toast.success}
                    />
                )}
            </Container>
        </AuthorizedRoute>
    );
}
