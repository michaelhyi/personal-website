import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { useState } from "react";
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
    const queryClient = useQueryClient();

    const { data, isLoading, isError } = useQuery({
        queryKey: ["readPost", params.get("id")],
        queryFn: async () => {
            if (params.get("mode") === "edit") {
                return readPost(params.get("id"));
            }

            return {
                title: "",
                content: "",
            };
        },
    });

    const [image, setImage] = useState(null);
    const [showImage, setShowImage] = useState(params.get("mode") === "edit");
    const editor = useEditor(data && `<h1>${data.title}</h1>${data.content}`);

    const { mutate, isPending } = useMutation({
        mutationFn: async () => {
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
                console.log(e);
                toast.custom(({ visible }) => (
                    <Toast
                        visible={visible}
                        message={e.response ? e.response.data : e.message}
                        success={false}
                    />
                ));
            }
        },
        onSuccess: () => {
            if (params.get("mode") === "edit") {
                queryClient.invalidateQueries(["readPost", params.get("id")]);
            } else {
                queryClient.invalidateQueries(["readAllPosts"]);
            }
        },
    });

    if (isLoading) return <Loading />;
    if (isError) return <NotFound />;

    return (
        <AuthorizedRoute>
            <Container>
                <BackButton href="/blog" text="Blog" />
                <Editor editor={editor} disabled={isPending} />
                <div className="mt-4" />
                <Dropzone
                    id={params.get("id")}
                    showImage={showImage}
                    setShowImage={setShowImage}
                    title={data && data.title}
                    submitting={isPending}
                    image={image}
                    setImage={setImage}
                />
                <button
                    type="submit"
                    onClick={mutate}
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
                    {isPending ? <Spinner /> : "Submit"}
                </button>
                <Toaster position="bottom-center" />
            </Container>
        </AuthorizedRoute>
    );
}
