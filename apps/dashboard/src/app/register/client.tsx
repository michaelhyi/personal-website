"use client";

import Image from "next/image";
import { useCallback, useState } from "react";
import type { FieldValues, SubmitHandler } from "react-hook-form";
import { useForm } from "react-hook-form";
import { register } from "services";

const RegisterClient = () => {
  const [error, setError] = useState<string | null>(null);
  const [submitting, setSubmitting] = useState<boolean>(false);

  const { register: formRegister, handleSubmit } = useForm({
    defaultValues: {
      email: "",
      password: "",
      confirmPassword: "",
    },
  });

  const handleRegister: SubmitHandler<FieldValues> = useCallback(
    async (data) => {
      await register(data, setSubmitting, setError);
    },
    [],
  );

  return (
    <div className="bg-neutral-800 text-white min-h-screen">
      <div
        className="absolute
   left-1/2
   top-1/2
   -translate-x-1/2
   -translate-y-1/2
   transform
   flex
   flex-col
   items-center
   text-center"
      >
        <Image
          alt="michael"
          className="rounded-full"
          height={100}
          src="/michael.png"
          width={100}
        />
        <div className="mt-4 text-2xl font-light">Michael Yi</div>
        <div className="mt-1 text-xs font-light text-neutral-400">
          Personal Website Dashboard
        </div>
        <div className="flex flex-col gap-4 text-left mt-6 text-xs text-neutral-300">
          <div>Email</div>
          <input
            className="bg-neutral-800 border-[1px] border-neutral-300 rounded-md w-64 h-10 px-2"
            id="email"
            disabled={submitting}
            {...formRegister("email")}
          />
          <div>Password</div>
          <input
            className="bg-neutral-800 border-[1px] border-neutral-300 rounded-md w-64 h-10 px-2"
            id="password"
            disabled={submitting}
            {...formRegister("password")}
            type="password"
          />
          <div>Confirm Password</div>
          <input
            className="bg-neutral-800 border-[1px] border-neutral-300 rounded-md w-64 h-10 px-2"
            id="confirmPassword"
            disabled={submitting}
            {...formRegister("confirmPassword")}
            type="password"
          />
          {error !== null && (
            <div className="text-xs text-red-300">{error}</div>
          )}
          <button
            onClick={(e) => handleSubmit(handleRegister)(e)}
            className="mt-2 bg-neutral-400 text-white h-10 font-semibold rounded-md duration-500 hover:opacity-75"
            type="submit"
          >
            Register
          </button>
        </div>
      </div>
    </div>
  );
};

export default RegisterClient;
