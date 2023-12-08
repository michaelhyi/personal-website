import Link from "next/link";
import { IoPencil, IoTrashOutline } from "react-icons/io5";

export default function Menu({
  id,
  handleToggleModal,
}: {
  id: number;
  handleToggleModal: (id?: number | undefined) => void;
}) {
  return (
    <div className="z-10 flex flex-col gap-2 absolute rounded-md shadow-lg w-36 bg-neutral-800 text-white font-medium overflow-hidden right-0 top-5 p-3 border-[1px]">
      <Link
        href={`/post?dialog=edit&id=${id}`}
        className="flex items-center gap-2 duration:500 hover:opacity-50 text-sm"
      >
        <IoPencil /> Edit Post
      </Link>
      <button
        onClick={() => {
          handleToggleModal(id);
        }}
        type="button"
        className="flex items-center gap-2 duration:500 hover:opacity-50 text-sm"
      >
        <IoTrashOutline />
        Delete Post
      </button>
    </div>
  );
}
