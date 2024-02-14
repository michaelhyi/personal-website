import { deletePost } from "@/services/post";
import { useRouter } from "next/navigation";
import { useCallback } from "react";
import { IoMdClose } from "react-icons/io";

export default function DeleteModal({
  id,
  modalOpen,
  handleToggleModal,
}: Readonly<{
  id: string;
  modalOpen: boolean;
  handleToggleModal: () => void;
}>) {
  const router = useRouter();

  const handleDelete = useCallback(async () => {
    await deletePost(id);
    handleToggleModal();
  }, [id, handleToggleModal, router]);

  return (
    <div
      className={`flex justify-center items-center fixed inset-0 ${
        modalOpen ? "z-50" : "-z-50"
      } bg-neutral-900/70`}
    >
      <div
        className={`w-2/5 bg-neutral-800 shadow-md rounded-xl overflow-y-auto max-h-[768px] ${
          modalOpen ? "animate-enter" : "animate-leave"
        }`}
      >
        <div className="flex mt-4 ml-6 mr-4">
          <div>
            <div className="text-xl font-semibold">Delete Post</div>
            <div className="text-xs font-light text-neutral-300">
              Are you sure? This action cannot be undone.
            </div>
          </div>
          <IoMdClose
            size={18}
            className="ml-auto cursor-pointer duration-500 hover:opacity-50"
            onClick={handleToggleModal}
          />
        </div>
        <div className="flex pr-4 pb-6 mt-12">
          <button
            onClick={handleDelete}
            type="button"
            className="ml-auto bg-red-400 px-4 py-3 rounded-lg duration-500 hover:opacity-50 text-sm font-semibold"
          >
            Delete
          </button>
        </div>
      </div>
    </div>
  );
}
