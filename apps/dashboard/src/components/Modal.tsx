"use client";

import { type ReactElement } from "react";
import type {
  FieldValues,
  SubmitHandler,
  UseFormHandleSubmit,
} from "react-hook-form";
import { IoMdClose } from "react-icons/io";

interface Props {
  title: string;
  body: ReactElement;
  modalOpen: boolean;
  handleToggleModal: () => void;
  action: (() => void) | SubmitHandler<FieldValues>;
  actionLabel: string;
  handleSubmit?: UseFormHandleSubmit<FieldValues>;
}

const Modal: React.FC<Props> = ({
  title,
  body,
  modalOpen,
  handleToggleModal,
  action,
  actionLabel,
  handleSubmit,
}) => {
  return (
    <div
      className={`flex justify-center items-center fixed inset-0 ${
        modalOpen ? "z-50" : "-z-50"
      } bg-neutral-800/70`}
    >
      <div
        className={`w-2/5 bg-neutral-700 shadow-lg rounded-xl duration-500 ${
          modalOpen ? "opacity-100" : "opacity-0"
        }`}
      >
        <div className="flex items-center mt-4 ml-6 mr-4">
          <div>
            <div className="text-lg font-semibold">{title}</div>
            <div className="text-xs font-light text-neutral-300">
              {title.includes("Delete")
                ? "Are you sure? This action cannot be undone."
                : null}
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
        <div className="mt-6 ml-6 text-xs font-light text-neutral-300">
          {!title.includes("Delete") ? body : null}
        </div>
        <div className="flex p-6">
          <button
            onClick={handleSubmit ? (e) => handleSubmit(action)(e) : action}
            type="button"
            className={`ml-auto ${
              actionLabel === "Submit" ? "bg-blue-400" : "bg-red-400"
            } p-3 rounded-lg duration-500 hover:opacity-50 text-sm`}
          >
            {actionLabel}
          </button>
        </div>
      </div>
    </div>
  );
};

export default Modal;
