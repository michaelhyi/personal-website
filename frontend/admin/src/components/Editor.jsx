import { EditorContent } from "@tiptap/react";
import "../css/editor.css";
import FaBold from "./Icons/FaBold";
import FaItalic from "./Icons/FaItalic";

export default function Editor({ editor, disabled }) {
        const CONTROLS = [
                {
                        id: "bold",
                        onClick: () =>
                                editor?.chain().focus().toggleBold().run(),
                        icon: FaBold,
                },
                {
                        id: "italic",
                        onClick: () =>
                                editor?.chain().focus().toggleItalic().run(),
                        icon: FaItalic,
                },
        ];

        return (
                <section className="editor-wrapper">
                        <section className="editor-header">
                                {CONTROLS.map(({ id, onClick, icon: Icon }) => (
                                        <button
                                                label="rtf control"
                                                className={`
                                editor-header-btn ${
                                        editor?.isActive(id)
                                                ? "editor-header-btn-active"
                                                : "editor-header-btn-inactive"
                                }`}
                                                onClick={onClick}
                                                disabled={disabled}
                                                type="button"
                                        >
                                                <Icon size={12} />
                                        </button>
                                ))}
                        </section>
                        <EditorContent editor={editor} />
                </section>
        );
}
