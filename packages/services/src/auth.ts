import type { Dispatch, SetStateAction } from "react";
import { signIn } from "next-auth/react";
import type { FieldValues } from "react-hook-form";

export const login = async (
  data: FieldValues,
  setSubmitting: Dispatch<SetStateAction<boolean>>,
  setError: Dispatch<SetStateAction<string | null>>,
): Promise<void> => {
  setSubmitting(true);

  const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/auth/login`, {
    body: JSON.stringify(data),
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
  });

  if (res.ok) {
    setError(null);
    localStorage.setItem("token", await res.text());

    await signIn("credentials", {
      ...data,
      redirect: true,
      callbackUrl: "/dashboard",
    });
  } else {
    setError(await res.text());
    setSubmitting(false);
  }
};

export const register = async (
  data: FieldValues,
  setSubmitting: Dispatch<SetStateAction<boolean>>,
  setError: Dispatch<SetStateAction<string | null>>,
): Promise<void> => {
  setSubmitting(true);

  const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/auth/register`, {
    body: JSON.stringify(data),
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
  });

  if (res.ok) {
    setError(null);
    localStorage.setItem("token", await res.text());

    await signIn("credentials", {
      ...data,
      redirect: true,
      callbackUrl: "/dashboard",
    });
  } else {
    setError(await res.text());
    setSubmitting(false);
  }
};
