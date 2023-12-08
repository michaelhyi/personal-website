import { deletePost } from "@personal-website/services";
import { useRouter } from "next/navigation";
import { useCallback } from "react";
import { IoMdClose } from "react-icons/io";

export default function DeleteModal({
  id,
  modalOpen,
  handleToggleModal,
}: {
  id: number;
  modalOpen: boolean;
  handleToggleModal: () => void;
}) {
  const router = useRouter();

  const handleDelete = useCallback(async () => {
    await deletePost(id);
    handleToggleModal();
    router.refresh();
  }, [id, handleToggleModal, router]);

  return (
    <div
      className={`flex justify-center items-center fixed inset-0 ${
        modalOpen ? "z-50" : "-z-50"
      } bg-neutral-800/70`}
    >
      <div
        className={`w-2/5 bg-neutral-700 shadow-lg rounded-xl duration-500 overflow-y-auto max-h-[768px] ${
          modalOpen ? "opacity-100" : "opacity-0"
        }`}
      >
        <div className="flex items-center mt-4 ml-6 mr-4">
          <div>
            <div className="text-lg font-semibold">Delete Post</div>
            <div className="text-xs font-light text-neutral-300">
              Are you sure? This action cannot be undone.
            </div>
          </div>
          <button
            type="button"
            className="ml-auto duration-500 hover:opacity-50"
            onClick={handleToggleModal}
          >
            <IoMdClose size={18} />
          </button>
        </div>
        <div className="flex p-6">
          <button
            onClick={handleDelete}
            type="button"
            className="ml-auto bg-red-400 p-3 rounded-lg duration-500 hover:opacity-50 text-sm"
          >
            Delete
          </button>
        </div>
      </div>
    </div>
  );
}
