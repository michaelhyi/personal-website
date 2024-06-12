import { AnimatePresence, motion } from "framer-motion";
import { useCallback } from "react";
import { IoMdClose } from "react-icons/io";
import { useNavigate } from "react-router-dom";
import { deletePost } from "../services/post";

export default function DeleteModal({ id, modalOpen, handleToggleModal }) {
    const navigate = useNavigate();

    const handleDeletePost = useCallback(async () => {
        await deletePost(id);
        handleToggleModal();
        navigate(0);
    }, []);

    return (
        <AnimatePresence>
            {modalOpen && (
                <motion.div
                    key="overlay"
                    className="flex justify-center items-center fixed inset-0 z-50 bg-neutral-900/70"
                    initial={{ opacity: 0 }}
                    animate={{ opacity: 1 }}
                    exit={{ opacity: 0 }}
                >
                    <motion.div
                        key="modal"
                        className="w-2/5 bg-neutral-800 shadow-md rounded-xl overflow-y-auto max-h-[768px]"
                        initial={{ opacity: 0, scale: 0.9 }}
                        animate={{ opacity: 1, scale: 1 }}
                        exit={{ opacity: 0, scale: 0.9 }}
                    >
                        <section className="flex mt-4 ml-6 mr-4">
                            <div>
                                <h3 className="text-xl font-semibold">
                                    Delete Post
                                </h3>
                                <p className="text-xs font-light text-neutral-300">
                                    Are you sure? This action cannot be undone.
                                </p>
                            </div>
                            <IoMdClose
                                size={18}
                                className="ml-auto cursor-pointer duration-500 hover:opacity-50"
                                onClick={handleToggleModal}
                            />
                        </section>
                        <section className="flex pr-4 pb-6 mt-12">
                            <button
                                onClick={handleDeletePost}
                                type="button"
                                className="ml-auto bg-red-400 px-4 py-3 rounded-lg duration-500 hover:opacity-50 text-sm font-semibold"
                            >
                                Delete
                            </button>
                        </section>
                    </motion.div>
                </motion.div>
            )}
        </AnimatePresence>
    );
}
