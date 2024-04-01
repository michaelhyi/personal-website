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
    const [title, setTitle] = useState(null);
    const [image, setImage] = useState(null);
    const [content, setContent] = useState(null);
    const [showImage, setShowImage] = useState(null);
    const [submitting, setSubmitting] = useState(false);

    const [loading, setLoading] = useState(true);
    const [notFound, setNotFound] = useState(false);

    const editor = useEditor(content);
    const [params] = useSearchParams();

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
        }

        setSubmitting(false);
    }, [setSubmitting, params, editor, image, showImage]);

    useEffect(() => {
        setShowImage(params.get("mode") === "edit");

        if (params.get("mode") === "edit") {
            (async () => {
                setLoading(true);

                const post = await readPost(params.get("id")).catch(() =>
                    setNotFound(true),
                );

                setTitle(post.title);
                setContent(`<h1>${post.title}</h1>${post.content}`);
            })();
        }

        setLoading(false);
    }, [params]);

    if (notFound) return <NotFound />;
    if (loading) return <Loading />;

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
                    title={title}
                    submitting={submitting}
                    setSubmitting={setSubmitting}
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
