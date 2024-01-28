"use client";

import Center from "@/components/Center";
import Container from "@/components/Container";
import Hoverable from "@/components/Hoverable";
import Spinner from "@/components/Spinner";
import Toast from "@/components/Toast";
import { signIn } from "next-auth/react";
import Image from "next/image";
import { useSearchParams } from "next/navigation";
import { useCallback, useEffect, useState } from "react";
import { Toaster, toast, useToasterStore } from "react-hot-toast";
import { FcGoogle } from "react-icons/fc";

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
    if (searchParams?.has("error"))
      toast.custom(({ visible }) => (
        <Toast
          visible={visible}
          message={searchParams.get("error")!}
          success={false}
        />
      ));
  }, [searchParams]);

  return (
    <Container absoluteFooter>
      <Center>
        <div className="flex flex-col items-center">
          <Image
            src="/michael.png"
            alt="michael"
            className="rounded-full"
            height={100}
            width={100}
          />
          <div className="mt-4 text-2xl font-medium">Michael Yi</div>
          <div className="mt-1 text-xs font-light text-neutral-500">
            Personal Website Admin
          </div>
          <Hoverable>
            <button
              type="button"
              onClick={handleClick}
              disabled={submitting}
              className="flex 
                         items-center
                         focus:outline-none
                         text-sm 
                         font-semibold 
                       bg-black 
                         shadow-md
                         rounded-md 
                         mt-4 
                         gap-3
                         border-[1px] 
                       border-white
                         px-6 
                         py-2"
            >
              {submitting ? <Spinner /> : <FcGoogle />}
            </button>
          </Hoverable>
        </div>
      </Center>
      <Toaster position="top-center" />
    </Container>
  );
}
