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
    <>
      <label htmlFor={id} className="text-sm">
        {capitalize(id)}
      </label>
      <input
        className="bg-neutral-800 border-[1px] border-neutral-300 rounded-md w-96 h-10 px-2 text-xs"
        id={id}
        disabled={submitting}
        type={id === "password" ? id : "text"}
        {...register(id)}
      />
    </>
  );
};

export default Input;
