"use client";

import Image from "next/image";
import { useSearchParams } from "next/navigation";
import { signIn } from "next-auth/react";
import { useCallback, useEffect, useState } from "react";
import { toast, Toaster, useToasterStore } from "react-hot-toast";
import { FcGoogle } from "react-icons/fc";
import Container from "@/components/Container";
import Spinner from "@/components/Spinner";
import ToastError from "@/components/toast/ToastError";

export default function AuthClient() {
  const { toasts } = useToasterStore();
  const searchParams = useSearchParams();
  const [submitting, setSubmitting] = useState<boolean>(false);

  const handleClick = useCallback(async () => {
    setSubmitting(true);

    await signIn("google");
  }, [setSubmitting]);

  useEffect(() => {
    toasts
      .filter((t) => t.visible)
      .filter((_, i) => i >= 1)
      .forEach((t) => {
        toast.dismiss(t.id);
      });
  }, [toasts]);

  useEffect(() => {
    if (searchParams && searchParams.has("error"))
      toast.custom(({ visible }) => (
        // eslint-disable-next-line @typescript-eslint/no-non-null-assertion -- searchParams.get("error") is not null if if statement is true
        <ToastError visible={visible} message={searchParams.get("error")!} />
      ));
  }, [searchParams]);

  return (
    <Container absoluteFooter>
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
          Personal Website Admin
        </div>
        <button
          type="button"
          onClick={handleClick}
          disabled={submitting}
          className="focus:outline-none mt-4 text-sm flex items-center gap-3 bg-neutral-800 text-white border-[1px] border-neutral-500 font-semibold px-6 py-2 rounded-md shadow-md duration-500 hover:opacity-50"
        >
          {submitting ? <Spinner /> : <FcGoogle />}
        </button>
      </div>
      <Toaster position="top-center" />
    </Container>
  );
}
