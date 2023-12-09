"use client";

import { Spinner } from "@chakra-ui/react";
import { Container } from "@personal-website/ui";
import Image from "next/image";
import { signIn } from "next-auth/react";
import { useCallback, useState } from "react";
import { FcGoogle } from "react-icons/fc";

export default function AuthClient() {
  //eslint-disable-next-line -- TODO: implement setError
  const [error] = useState<string | null>(null);
  const [submitting, setSubmitting] = useState<boolean>(false);

  const handleClick = useCallback(async () => {
    setSubmitting(true);

    await signIn("google");
  }, [setSubmitting]);

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
          {submitting ? <Spinner size="xs" /> : <FcGoogle />}
        </button>
        <div className="text-red-500 text-xs mt-4 font-light">{error}</div>
      </div>
    </Container>
  );
}
