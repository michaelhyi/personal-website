" use client";

import type { FC } from "react";
import type { FieldValues, UseFormRegister } from "react-hook-form";
import { capitalize } from "@/utils/capitialize";

interface Props {
  id: string;
  register: UseFormRegister<FieldValues>;
  submitting: boolean;
}

const Input: FC<Props> = ({ id, register, submitting }) => {
  return (
    <div className="flex flex-col gap-2">
      <label htmlFor={id} className="font-semibold text-sm">
        {capitalize(id)}
      </label>
      <input
        className="bg-neutral-700 border-[1px] border-neutral-300 rounded-md w-72 h-9 px-2 text-xs"
        id={id}
        disabled={submitting}
        type={id === "password" ? id : "text"}
        {...register(id)}
      />
    </div>
  );
};

export default Input;
