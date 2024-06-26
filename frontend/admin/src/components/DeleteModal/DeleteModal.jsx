import "./DeleteModal.css";

import { useCallback } from "react";
import { useNavigate } from "react-router-dom";

import IoMdClose from "../Icons/IoMdClose";
import { deletePost } from "../../services/post";

export default function DeleteModal({ id, modalOpen, handleToggleModal }) {
    const navigate = useNavigate();

    const handleDeletePost = useCallback(async () => {
        await deletePost(id);
        handleToggleModal();
        navigate(0);
    }, [id, navigate]);

    return (
        modalOpen && (
            <div className="delete-modal-wrapper">
                <div className="delete-modal-body">
                    <section className="delete-modal-header">
                        <div>
                            <h3 className="delete-modal-header-title">
                                Delete Post
                            </h3>
                            <p className="delete-modal-header-subittle">
                                Are you sure? This action cannot be undone.
                            </p>
                        </div>
                        <button
                            aria-label="Close Modal"
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
