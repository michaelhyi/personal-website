import IoPencil from "../assets/icons/IoPencil";
import IoTrashOutline from "../assets/icons/IoTrashOutline";
import Hoverable from "./Hoverable";

export default function Menu({ id, handleToggleModal }) {
    return (
        <aside
            className="z-[1000]
                    flex 
                    flex-col 
                    gap-2 
                    absolute 
                    rounded-md 
                    shadow-lg 
                    w-36 
                    bg-neutral-800
                    text-white 
                    font-medium 
                    overflow-hidden 
                    right-0 
                    top-5 
                    p-3"
        >
            <Hoverable>
                <a
                    href={`/blog/post?id=${id}`}
                    className="flex items-center gap-2 text-sm"
                >
                    <IoPencil /> Edit Post
                </a>
            </Hoverable>
            <Hoverable>
                <button
                    onClick={() => {
                        handleToggleModal(id);
                    }}
                    type="button"
                    className="flex items-center gap-2 text-sm"
                >
                    <IoTrashOutline />
                    Delete Post
                </button>
            </Hoverable>
        </aside>
    );
}
