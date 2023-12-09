"use client";

import {
  createPost,
  createPostImage,
  updatePost,
} from "@personal-website/services";
import { Container } from "@personal-website/ui";
import type { Editor as EditorType } from "@tiptap/react";
import { useCallback, useState } from "react";
import Dropzone from "@/components/Dropzone";
import Editor from "@/components/Editor";
import { useEditor } from "@/hooks/useEditor";

export default function CreateEditBlogClient({
  id,
  title: postTitle,
  content,
}: {
  id: number | null;
  title: string | null;
  content: string | null;
}) {
  const [image, setImage] = useState<File | null>(null);
  const [showImage, setShowImage] = useState<boolean>(id !== null);
  const [submitting, setSubmitting] = useState<boolean>(false);
  const editor: EditorType | null = useEditor(content);

  const handleClick = useCallback(async () => {
    const text = editor?.getHTML();

    if (text) {
      if (id) {
        try {
          await updatePost(id, text);

          if (image) {
            const formData = new FormData();
            formData.append("file", image);
            await createPostImage(id, formData);
          }
        } catch {
          throw new Error("Failed to update post");
        }
      } else {
        let postId: number;

        try {
          postId = await createPost(text);

          if (image) {
            const formData = new FormData();
            formData.append("file", image);
            await createPostImage(postId, formData);
          }
        } catch {
          throw new Error("Failed to create post");
        }
      }
    }
  }, [id, editor, image]);

  return (
    <Container>
      <Editor editor={editor} />
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
        Submit
      </button>
    </Container>
  );
}
