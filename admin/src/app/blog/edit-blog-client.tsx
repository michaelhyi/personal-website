"use client";

import BackButton from "@/components/BackButton";
import Container from "@/components/Container";
import Dropzone from "@/components/Dropzone";
import Editor from "@/components/Editor";
import Loading from "@/components/Loading";
import Spinner from "@/components/Spinner";
import Toast from "@/components/Toast";
import { authenticate, validateToken } from "@/services/auth";
import { createPost, createPostImage, updatePost } from "@/services/post";
import type { User } from "@/types/user";
import Document from "@tiptap/extension-document";
import Placeholder from "@tiptap/extension-placeholder";
import { useEditor, type Editor as EditorType } from "@tiptap/react";
import StarterKit from "@tiptap/starter-kit";
import { signOut } from "next-auth/react";
import { useCallback, useEffect, useState } from "react";
import { useForm, type FieldValues, type SubmitHandler } from "react-hook-form";
import { Toaster, toast } from "react-hot-toast";

//TODO: refactor

const CustomDocument = Document.extend({
  content: "heading block*",
});

const validateForm = (
  text: string | undefined,
  image: File | null,
  showImage: boolean
) => {
  const titleIndex = text!.search("</h1>");
  const title = text!.substring(4, titleIndex);
  const content = text!.substring(titleIndex + 5);

  if (!title || title.length === 0) throw new Error("A title is required.");

  if (!content || content.length === 0)
    throw new Error("Post content is required.");

  if (!image && !showImage) throw new Error("An image is required.");
};

export default function EditBlogClient({
  user,
  id,
  title: postTitle,
  content,
}: Readonly<{
  user: User;
  id: string | null;
  title: string | null;
  content: string | null;
}>) {
  const [loading, setLoading] = useState<boolean>(true);
  const [image, setImage] = useState<File | null>(null);
  const [showImage, setShowImage] = useState<boolean>(id !== null);
  const [submitting, setSubmitting] = useState<boolean>(false);
  const editor: EditorType | null = useEditor({
    content,
    editorProps: {
      attributes: {
        class:
          "prose dark:prose-invert prose-sm mx-[1.5vw] my-5 focus:outline-none",
      },
    },

    extensions: [
      CustomDocument,
      Placeholder.configure({
        placeholder: ({ node }) => {
          if (node.type.name === "heading") {
            return "Enter your title here...";
          }

          return "Start writing here...";
        },
        emptyNodeClass:
          "cursor-text before:content-[attr(data-placeholder)] before:absolute before:text-mauve-11 before:opacity-50 before-pointer-events-none",
      }),
      StarterKit.configure({ document: false }),
    ],
  });

  const { register, handleSubmit } = useForm({
    defaultValues: {
      id: "",
    },
  });

  const handleClick: SubmitHandler<FieldValues> = useCallback(
    async (data) => {
      setSubmitting(true);

      const text = editor?.getHTML();

      try {
        validateForm(text, image, showImage);
      } catch (e) {
        toast.custom(({ visible }) => (
          // @ts-expect-error -- e is of type Error
          <Toast visible={visible} message={e.message} success={false} />
        ));
        setSubmitting(false);
        return;
      }

      try {
        if (id) {
          await updatePost(id, text!);
        } else {
          await createPost(data.id, text!);
        }

        if (image) {
          const formData = new FormData();
          formData.append("file", image);
          await createPostImage(id ?? data.id, formData);
        }

        toast.custom(({ visible }) => (
          <Toast
            visible={visible}
            message={`Post successfully ${id ? "updated" : "published"}!`}
            success
          />
        ));
      } catch (e) {
        toast.custom(({ visible }) => (
          // @ts-expect-error -- e is of type AxiosError
          <Toast visible={visible} message={e.response.data} success={false} />
        ));
      }

      setSubmitting(false);
    },
    [setSubmitting, id, editor, image, showImage]
  );

  useEffect(() => {
    let token: string | null = localStorage.getItem("token");

    if (!token) {
      void (async () => {
        token = await authenticate(user.username);
        localStorage.setItem("token", token);
        setLoading(false);
      })();
    } else {
      void (async () => {
        try {
          const validToken = await validateToken(token);

          if (!validToken) {
            localStorage.removeItem("token");
            await signOut();
          } else {
            setLoading(false);
          }
        } catch {
          localStorage.removeItem("token");
          await signOut();
        }
      })();
    }
  }, [user.username]);

  if (loading) {
    return <Loading />;
  }

  return (
    <Container>
      <BackButton href="/blog" text="Blog" />
      {id ? null : (
        <div className="flex flex-col mt-10">
          <label htmlFor="id" className="font-normal text-sm">
            Post ID
          </label>
          <input
            id="id"
            {...register("id")}
            disabled={submitting}
            className="focus:outline-none mt-2 bg-black border-[1px] border-neutral-600 rounded-md shadow-lg mb-5 w-72 px-2 py-2 text-sm font-light text-neutral-200"
          />
        </div>
      )}
      <Editor editor={editor} disabled={submitting} />
      <div className="mt-4" />
      <Dropzone
        id={id}
        showImage={showImage}
        setShowImage={setShowImage}
        title={postTitle}
        submitting={submitting}
        setSubmitting={setSubmitting}
        image={image}
        setImage={setImage}
      />
      <button
        type="submit"
        onClick={(e) => handleSubmit(handleClick)(e)}
        className="mt-12
                     ml-auto
                     text-sm
                     flex
                     items-center 
                     gap-3 
                     bg-black 
                     text-white 
                     border-[1px] 
                     border-neutral-500 
                     font-semibold 
                     px-6
                     py-2
                     rounded-md 
                     shadow-md 
                     duration-500 
                     hover:opacity-50"
      >
        {submitting ? <Spinner /> : "Submit"}
      </button>
      <Toaster position="bottom-center" />
    </Container>
  );
}
