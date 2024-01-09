"use client";

import { EditorContent, type Editor as EditorType } from "@tiptap/react";
import { FaBold, FaItalic } from "react-icons/fa";

export default function Editor({
  editor,
  disabled,
}: {
  editor: EditorType | null;
  disabled: boolean;
}) {
  return (
    <div className="bg-black border-[1px] border-neutral-600 rounded-md shadow-lg">
      <div className="flex gap-2 mx-[1.5vw] my-5">
        <button
          type="button"
          onClick={() => editor?.chain().focus().toggleBold().run()}
          disabled={disabled}
          className={`duration-500 ${
            editor?.isActive("bold") ? "text-white" : "text-neutral-500"
          }`}
        >
          <FaBold size={12} />
        </button>
        <button
          type="button"
          onClick={() => editor?.chain().focus().toggleItalic().run()}
          disabled={disabled}
          className={`duration-500 ${
            editor?.isActive("italic") ? "text-white" : "text-neutral-500"
          }`}
        >
          <FaItalic size={12} />
        </button>
      </div>
      <EditorContent editor={editor} />
    </div>
  );
}
