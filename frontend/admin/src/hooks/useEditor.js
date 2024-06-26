import "./editor.css";

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
                    class: "editor-props",
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
                    emptyNodeClass: "empty-node",
                }),
                StarterKit.configure({ document: false }),
            ],
        },
        [content],
    );
}

export default useEditor;
