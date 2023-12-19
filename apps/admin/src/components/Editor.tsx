import { EditorContent, type Editor as EditorType } from "@tiptap/react";
import { FaBold, FaItalic } from "react-icons/fa";

export default function Editor({ editor }: { editor: EditorType | null }) {
  return (
    <div className="bg-neutral-800 border-[1px] border-neutral-600 rounded-md shadow-lg">
      <div className="flex gap-2 mx-[0.75vw] my-5">
        <button
          type="button"
          onClick={() => editor?.chain().focus().toggleBold().run()}
          // disabled={!editor.can().chain().focus().toggleBold().run()}
          className={`duration-500 ${
            editor?.isActive("bold") ? "text-white" : "text-neutral-500"
          }`}
        >
          <FaBold size={12} />
        </button>
        <button
          type="button"
          onClick={() => editor?.chain().focus().toggleItalic().run()}
          // disabled={!editor.can().chain().focus().toggleItalic().run()}
          className={`duration-500 ${
            editor?.isActive("bold") ? "text-white" : "text-neutral-500"
          }`}
        >
          <FaItalic size={12} />
        </button>
      </div>
      <EditorContent editor={editor} />
    </div>
  );
}
