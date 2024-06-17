import { EditorContent } from "@tiptap/react";
import FaBold from "../assets/icons/FaBold";
import FaItalic from "../assets/icons/FaItalic";
import Hoverable from "./Hoverable";

export default function Editor({ editor, disabled }) {
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
        <section className="mt-8 bg-neutral-800 rounded-md shadow-sm">
            <section className="flex gap-2 mx-[1.5vw] my-5">
                {controls.map(({ id, onClick, icon: Icon }) => (
                    <Hoverable key={id}>
                        <button
                            label="rtf control"
                            className={`duration-500 ${
                                editor?.isActive(id)
                                    ? "text-white"
                                    : "text-neutral-500"
                            }`}
                            onClick={onClick}
                            disabled={disabled}
                            type="button"
                        >
                            <Icon size={12} />
                        </button>
                    </Hoverable>
                ))}
            </section>
            <EditorContent editor={editor} />
        </section>
    );
}
