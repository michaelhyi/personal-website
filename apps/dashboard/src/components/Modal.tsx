"use client";

import { useRouter } from "next/navigation";
import { useCallback } from "react";
import { IoMdClose } from "react-icons/io";
import { IoWarningOutline } from "react-icons/io5";
import { deletePost } from "services";

interface Props {
  showModal: boolean;
  handleToggle: () => void;
  id: number;
}

const Modal: React.FC<Props> = ({ showModal, handleToggle, id }) => {
  const router = useRouter();

  const handleDelete = useCallback(async () => {
    await deletePost(id);
    router.push("/dashboard/blog");
  }, [id, router]);

  return (
    <div
      className={`
    justify-center 
    items-center 
    flex 
    overflow-x-hidden 
    overflow-y-auto 
    fixed 
    inset-0 
    ${showModal ? "z-50" : "-z-50"}
    outline-none 
    focus:outline-none
    bg-neutral-800/70
  `}
    >
      <div
        className="
    relative 
    w-full
    md:w-4/6
    lg:w-3/6
    xl:w-2/5
    my-6
    mx-auto 
    h-full 
    lg:h-auto
    md:h-auto
    "
      >
        {/*content*/}
        <div
          className={`
      translate
      duration-300
      h-full
          bg-neutral-700

      ${showModal ? "translate-y-0" : "translate-y-full"}
      ${showModal ? "opacity-100" : "opacity-0"}
    `}
        >
          <div
            className="
        translate
        h-full
        lg:h-auto
        md:h-auto
        border-0 
        rounded-lg 
        shadow-lg 
        relative 
        flex 
        flex-col 
        w-full 
        bg-neutral-700 
        outline-none 
        focus:outline-none
      "
          >
            {/*header*/}
            <div
              className="
          flex 
          items-center 
          p-6
          rounded-t
          justify-center
          relative
          border-b-[1px]
          "
            >
              <button
                type="button"
                className="
              p-1
              border-0 
              hover:opacity-70
              transition
              absolute
              left-9
            "
                onClick={handleToggle}
              >
                <IoMdClose size={18} />
              </button>
              <div className="text-lg font-semibold">Delete Post</div>
            </div>
            {/*body*/}
            <div className="mx-auto mt-6">
              <IoWarningOutline size={75} />
            </div>
            <div className="relative mt-4 mx-auto font-medium text-lg w-96 text-center">
              Are you sure?
              <br />
              This action cannot be undone.
            </div>
            {/*footer*/}
            <div className="flex flex-col gap-2 p-6 mt-8">
              <div
                className="
              flex 
              flex-row 
              items-center 
              gap-4 
              w-full
            "
              >
                <button
                  onClick={handleToggle}
                  type="button"
                  className="ml-auto bg-neutral-400 px-3 py-3 rounded-lg duration-500 hover:opacity-50"
                >
                  Cancel
                </button>
                <button
                  onClick={handleDelete}
                  type="button"
                  className=" bg-red-400 px-3 py-3 rounded-lg duration-500 hover:opacity-50"
                >
                  Delete
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Modal;
