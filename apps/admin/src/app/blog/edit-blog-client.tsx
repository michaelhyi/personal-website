/* eslint-disable @typescript-eslint/no-unsafe-assignment -- e is an instanceof Error or AxiosError */
/* eslint-disable @typescript-eslint/no-unsafe-member-access -- e is an instanceof Error or AxiosError */
/* eslint-disable @typescript-eslint/no-non-null-assertion -- all non-null assertions are true */

"use client";

import {
  authenticate,
  createPost,
  createPostImage,
  updatePost,
  validateToken,
} from "@personal-website/services";
import { BackButton, Container, Loading, Spinner } from "@personal-website/ui";
import type { User } from "@personal-website/types";
import type { Editor as EditorType } from "@tiptap/react";
import { signOut } from "next-auth/react";
import { useCallback, useEffect, useState } from "react";
import { toast, Toaster } from "react-hot-toast";
import Dropzone from "@/components/Dropzone";
import Editor from "@/components/Editor";
import useEditor from "@/hooks/useEditor";
import { validateForm } from "@/utils/validateForm";
import ToastError from "@/components/toast/ToastError";
import ToastSuccess from "@/components/toast/ToastSuccess";

export default function EditBlogClient({
  user,
  id,
  title: postTitle,
  content,
}: {
  user: User;
  id: number | null;
  title: string | null;
  content: string | null;
}) {
  const [loading, setLoading] = useState<boolean>(true);
  const [image, setImage] = useState<File | null>(null);
  const [showImage, setShowImage] = useState<boolean>(id !== null);
  const [submitting, setSubmitting] = useState<boolean>(false);
  const editor: EditorType | null = useEditor(content);

  const handleClick = useCallback(async () => {
    setSubmitting(true);
    const text = editor?.getHTML();

    try {
      validateForm(text, image, showImage);
    } catch (e) {
      toast.custom(({ visible }) => (
        // @ts-expect-error -- e is of type Error
        <ToastError visible={visible} message={e.message} />
      ));
      setSubmitting(false);
      return;
    }

    try {
      let postId: number;

      if (id) {
        await updatePost(id, text!);
      } else {
        postId = await createPost(text!);
      }

      if (image) {
        const formData = new FormData();
        formData.append("file", image);
        await createPostImage(id || postId!, formData);
      }

      toast.custom(({ visible }) => (
        <ToastSuccess
          visible={visible}
          message={`Post successfully ${id ? "updated" : "published"}!`}
        />
      ));
    } catch (e) {
      toast.custom(({ visible }) => (
        // @ts-expect-error -- e is of type AxiosError
        <ToastError visible={visible} message={e.response.data} />
      ));
    }

    setSubmitting(false);
  }, [setSubmitting, id, editor, image, showImage]);

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
      <div className="mt-8 font-bold text-3xl">Create or Edit Post</div>
      <div className="mt-2 mb-8 text-sm font-medium text-neutral-400">
        Craft and customize your written works.
      </div>
      <Editor editor={editor} />
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
        onClick={handleClick}
        className="mt-12
                     ml-auto
                     text-sm
                     flex
                     items-center 
                     gap-3 
                     bg-neutral-800 
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
