import Document from "@tiptap/extension-document";
import Placeholder from "@tiptap/extension-placeholder";
import { useEditor } from "@tiptap/react";
import StarterKit from "@tiptap/starter-kit";
import { useCallback, useEffect, useState } from "react";
import { Toaster, toast } from "react-hot-toast";
import { useSearchParams } from "react-router-dom";
import BackButton from "../components/BackButton";
import Center from "../components/Center";
import Container from "../components/Container";
import Dropzone from "../components/Dropzone";
import Editor from "../components/Editor";
import Loading from "../components/Loading";
import Spinner from "../components/Spinner";
import Toast from "../components/Toast";
import {
  createPost,
  createPostImage,
  readPost,
  updatePost,
} from "../services/post";

const CustomDocument = Document.extend({
  content: "heading block*",
});

const validateForm = (text, image, showImage) => {
  const titleIndex = text.search("</h1>");
  const title = text.substring(4, titleIndex);
  const content = text.substring(titleIndex + 5);

  if (!title || title.length === 0) throw new Error("A title is required.");

  if (!content || content.length === 0)
    throw new Error("Post content is required.");

  if (!image && !showImage) throw new Error("An image is required.");
};

export default function Post() {
  const [params] = useSearchParams();
  const [notFound, setNotFound] = useState(false);
  const [loading, setLoading] = useState(true);
  const [title, setTitle] = useState(null);
  const [content, setContent] = useState(null);
  const [image, setImage] = useState(null);
  const [showImage, setShowImage] = useState(null);
  const [submitting, setSubmitting] = useState(false);
  const editor = useEditor(
    {
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
    },
    [content]
  );

  const handleClick = useCallback(async () => {
    setSubmitting(true);

    const text = editor?.getHTML();

    try {
      validateForm(text, image, showImage);
    } catch (e) {
      toast.custom(({ visible }) => (
        <Toast visible={visible} message={e.message} success={false} />
      ));
      setSubmitting(false);
      return;
    }

    try {
      let postId;
      if (params.get("id")) {
        await updatePost(params.get("id"), text);
      } else {
        postId = await createPost(text);
      }

      if (image) {
        const formData = new FormData();
        formData.append("file", image);
        await createPostImage(params.get("id") ?? postId, formData);
      }

      toast.custom(({ visible }) => (
        <Toast
          visible={visible}
          message={`Post successfully ${
            params.get("id") ? "updated" : "published"
          }!`}
          success
        />
      ));
    } catch (e) {
      toast.custom(({ visible }) => (
        <Toast visible={visible} message={e.response.data} success={false} />
      ));
    }

    setSubmitting(false);
  }, [setSubmitting, params, editor, image, showImage]);

  useEffect(() => {
    (async () => {
      if (params.get("id")) {
        setLoading(true);
        setShowImage(true);
        try {
          const post = await readPost(params.get("id")).catch(() => {});
          setTitle(post.title);
          setContent(`<h1>${post.title}</h1>${post.content}`);
          setLoading(false);
        } catch {
          setNotFound(true);
        }
      } else {
        setLoading(false);
        setShowImage(false);
      }
    })();
  }, [params, editor]);

  if (notFound) {
    return (
      <Container absoluteFooter>
        <Center>
          <div className="text-[10px] text-neutral-300">Not Found</div>
        </Center>
      </Container>
    );
  }

  if (loading) return <Loading />;

  return (
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
        onClick={handleClick}
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
  );
}
