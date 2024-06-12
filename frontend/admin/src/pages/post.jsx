import { useCallback, useEffect, useState } from "react";
import { Toaster, toast } from "react-hot-toast";
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

            toast.custom(({ visible }) => (
                <Toast
                    success
                    visible={visible}
                    message={`Post successfully ${
                        params.get("mode") === "create"
                            ? "published"
                            : "updated"
                    }!`}
                />
            ));
        } catch (e) {
            toast.custom(({ visible }) => (
                <Toast
                    visible={visible}
                    message={e.response ? e.response.data : e.message}
                    success={false}
                />
            ));
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
                <Toaster position="bottom-center" />
            </Container>
        </AuthorizedRoute>
    );
}
