"use client";

import { useRouter } from "next/navigation";
import { useCallback, type ReactElement } from "react";
import { IoMdClose } from "react-icons/io";

interface Props {
  title: string;
  description: string;
  action: (id: number) => Promise<void>;
  showModal: boolean;
  handleToggle: () => void;
  id: number;
  callbackUrl: string;
  body: ReactElement;
}

const Modal: React.FC<Props> = ({
  title,
  description,
  action,
  showModal,
  handleToggle,
  id,
  callbackUrl,
  body,
}) => {
  const router = useRouter();

  const handleDelete = useCallback(async () => {
    await action(id);
    router.push(`/dashboard${callbackUrl}`);
  }, [id, router, action, callbackUrl]);

  return (
    <div
      className={`flex justify-center items-center fixed inset-0 ${
        showModal ? "z-50" : "-z-50"
      } bg-neutral-800/70`}
    >
      <div
        className={`w-2/5 bg-neutral-700 shadow-lg rounded-xl duration-500 ${
          showModal ? "opacity-100" : "opacity-0"
        }`}
      >
        <div className="flex mt-4 ml-6 mr-4">
          <div>
            <div className="text-lg font-semibold">{title}</div>
            <div className="text-xs font-light text-neutral-300 mt-1">
              {description}
            </div>
          </div>
          <button
            type="button"
            className="ml-auto duration-500 hover:opacity-50"
            onClick={handleToggle}
          >
            <IoMdClose size={18} />
          </button>
        </div>
        {/*body*/}
        <div className="mt-12">{body}</div>
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
  );
};

export default Modal;
