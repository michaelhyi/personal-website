"use client";

import Image from "next/image";
import { useForm } from "react-hook-form";
import type { FieldValues, SubmitHandler } from "react-hook-form";
import type { FC } from "react";
import { useCallback, useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import type { AxiosError } from "axios";
import Container from "@/components/Container";
import { auth } from "@/services/auth";
import type User from "@/types/user";

interface Props {
  user: User | null;
}

const RegisterClient: FC<Props> = ({ user }) => {
  const router = useRouter();
  const [error, setError] = useState<string | null>(null);
  const [submitting, setSubmitting] = useState<boolean>(false);

  useEffect(() => {
    if (user) {
      router.push("/dashboard");
    }
  }, [router, user]);

  const { register, handleSubmit } = useForm({
    defaultValues: {
      email: "",
      password: "",
      confirmPassword: "",
    },
  });

  const handleLogin: SubmitHandler<FieldValues> = useCallback(
    async (data) => {
      setSubmitting(true);

      try {
        await auth("register", data);
        setError(null);
        router.push("/");
      } catch (e) {
        const { response } = e as AxiosError;
        if (response) {
          setError(response.data as string);
        }
        setSubmitting(false);
      }
    },
    [router],
  );

  return (
    <Container>
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
            {...register("email")}
          />
          <div>Password</div>
          <input
            className="bg-neutral-800 border-[1px] border-neutral-300 rounded-md w-64 h-10 px-2"
            id="password"
            disabled={submitting}
            {...register("password")}
            type="password"
          />
          <div>Confirm Password</div>
          <input
            className="bg-neutral-800 border-[1px] border-neutral-300 rounded-md w-64 h-10 px-2"
            id="confirmPassword"
            disabled={submitting}
            {...register("confirmPassword")}
            type="password"
          />
          {error !== null && (
            <div className="text-xs text-red-300">{error}</div>
          )}
          <button
            onClick={(e) => handleSubmit(handleLogin)(e)}
            className="mt-2 bg-neutral-400 text-white h-10 font-semibold rounded-md duration-500 hover:opacity-75"
            type="submit"
          >
            Register
          </button>
        </div>
      </div>
    </Container>
  );
};

export default RegisterClient;
