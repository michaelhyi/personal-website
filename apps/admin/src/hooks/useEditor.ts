/* eslint-disable import/no-named-as-default -- all default exports are correctly exported */
import Document from "@tiptap/extension-document";
import Placeholder from "@tiptap/extension-placeholder";
import { type Editor, useEditor as editor } from "@tiptap/react";
import StarterKit from "@tiptap/starter-kit";

const CustomDocument = Document.extend({
  content: "heading block*",
});

export const useEditor = (content: string | null): Editor | null => {
  return editor({
    extensions: [
      CustomDocument,
      StarterKit.configure({ document: false }),
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
    ],
    content,
    editorProps: {
      attributes: {
        class:
          "prose dark:prose-invert prose-sm mx-[0.75vw] my-5 focus:outline-none",
      },
    },
  });
};
