/* eslint-disable @typescript-eslint/no-unsafe-member-access -- e is an instanceof Error or AxiosError */
/* eslint-disable @typescript-eslint/no-non-null-assertion -- all non-null assertions are true */

"use client";

import {
  createPost,
  createPostImage,
  readPost,
  updatePost,
} from "@personal-website/services";
import { BackButton, Container, Spinner } from "@personal-website/ui";
import type { Editor as EditorType } from "@tiptap/react";
import { useRouter } from "next/navigation";
import { useCallback, useState } from "react";
import { toast, Toaster } from "react-hot-toast";
import Dropzone from "@/components/Dropzone";
import Editor from "@/components/Editor";
import { useEditor } from "@/hooks/useEditor";
import { validateForm } from "@/utils/validateForm";

export default function EditBlogClient({
  id,
  title: postTitle,
  content,
}: {
  id: number | null;
  title: string | null;
  content: string | null;
}) {
  const router = useRouter();
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
      toast.custom((t) => (
        <div
          className={`${
            t.visible ? "animate-enter" : "animate-leave"
          }  bg-red-400 
          shadow-lg 
          rounded-lg 
          flex 
          justify-center`}
        >
          <div className="px-5 py-3 font-semibold text-sm">
            {
              // @ts-expect-error -- e is of type Error
              e.message
            }
          </div>
        </div>
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

      const post = await readPost(id || postId!);

      router.push(`${process.env.NEXT_PUBLIC_WEB_URL}/blog/${post!.title}`);
    } catch (e) {
      toast.custom((t) => (
        <div
          className={`${
            t.visible ? "animate-enter" : "animate-leave"
          }  bg-red-400 
          shadow-lg 
          rounded-lg 
          flex 
          justify-center`}
        >
          <div className="px-5 py-3 font-semibold text-sm">
            {
              // @ts-expect-error -- e is of type AxiosError
              e.response.data
            }
          </div>
        </div>
      ));
      setSubmitting(false);
    }
  }, [setSubmitting, id, editor, image, showImage, router]);

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
