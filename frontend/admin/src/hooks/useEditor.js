import { useEditor as editor } from "@tiptap/react";
import Document from "@tiptap/extension-document";
import Placeholder from "@tiptap/extension-placeholder";
import StarterKit from "@tiptap/starter-kit";

const CustomDocument = Document.extend({
    content: "heading block*",
});

function useEditor(content) {
    return editor(
        {
            content,
            editorProps: {
                attributes: {
                    class: "prose dark:prose-invert prose-sm mx-[1.5vw] my-5 focus:outline-none",
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
        [content],
    );
}

export default useEditor;
