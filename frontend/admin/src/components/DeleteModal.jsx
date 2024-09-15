import "../css/delete-modal.css";
import { useCallback } from "react";
import IoMdClose from "./Icons/IoMdClose";
import { deletePost } from "../js/post-service";

export default function DeleteModal({ id, modalOpen, handleToggleModal }) {
    const handleDeletePost = useCallback(async () => {
        await deletePost(id);
        handleToggleModal();
        window.location.href = "/blog";
    }, [id]);

    return (
        modalOpen && (
            <div className="delete-modal-wrapper">
                <div className="delete-modal-body">
                    <section className="delete-modal-header">
                        <div>
                            <h3 className="delete-modal-header-title">
                                Delete Post
                            </h3>
                            <p className="delete-modal-header-subtitle">
                                Are you sure? This action cannot be undone.
                            </p>
                        </div>
                        <button
                            aria-label="close modal btn"
                            type="button"
                            className="close-modal-btn"
                            onClick={handleToggleModal}
                        >
                            <IoMdClose />
                        </button>
                    </section>
                    <section className="delete-modal-footer">
                        <button
                            onClick={handleDeletePost}
                            type="button"
                            className="delete-btn"
                        >
                            Delete
                        </button>
                    </section>
                </div>
            </div>
        )
    );
}
