"use client";

import { EditorContent, type Editor as EditorType } from "@tiptap/react";
import { FaBold, FaItalic } from "react-icons/fa";
import Hoverable from "./Hoverable";

export default function Editor({
  editor,
  disabled,
}: Readonly<{
  editor: EditorType | null;
  disabled: boolean;
}>) {
  const controls = [
    {
      id: "bold",
      onClick: () => editor?.chain().focus().toggleBold().run(),
      icon: FaBold,
    },
    {
      id: "italic",
      onClick: () => editor?.chain().focus().toggleItalic().run(),
      icon: FaItalic,
    },
  ];

  return (
    <div className="bg-black rounded-md shadow-lg border-[1px] border-neutral-600">
      <div className="flex gap-2 mx-[1.5vw] my-5">
        {controls.map(({ id, onClick, icon: Icon }) => (
          <Hoverable key={id}>
            <button
              className={`duration-500 ${
                editor?.isActive(id) ? "text-white" : "text-neutral-500"
              }`}
              onClick={onClick}
              disabled={disabled}
              type="button"
            >
              <Icon size={12} />
            </button>
          </Hoverable>
        ))}
      </div>
      <EditorContent editor={editor} />
    </div>
  );
}
