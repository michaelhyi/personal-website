import axios from "axios";
import type { Dispatch, SetStateAction } from "react";
import { signIn } from "next-auth/react";
import type { FieldValues } from "react-hook-form";

export const login = async (
  data: FieldValues,
  setSubmitting: Dispatch<SetStateAction<boolean>>,
  setError: Dispatch<SetStateAction<string | null>>
): Promise<void> => {
  setSubmitting(true);

  const res = await axios.post(
    `${process.env.NEXT_PUBLIC_API_URL}/auth/login`,
    data
  );

  if (res.status === 200) {
    setError(null);
    localStorage.setItem("token", res.data);

    await signIn("credentials", {
      ...data,
      redirect: true,
      callbackUrl: "/dashboard",
    });
  } else {
    setError(res.data);
    setSubmitting(false);
  }
};

export const register = async (
  data: FieldValues,
  setSubmitting: Dispatch<SetStateAction<boolean>>,
  setError: Dispatch<SetStateAction<string | null>>
): Promise<void> => {
  setSubmitting(true);

  const res = await axios.post(
    `${process.env.NEXT_PUBLIC_API_URL}/auth/register`,
    data,
    {
      headers: {
        "Content-Type": "application/json",
      },
    }
  );

  if (res.status === 200) {
    setError(null);
    localStorage.setItem("token", res.data);

    await signIn("credentials", {
      ...data,
      redirect: true,
      callbackUrl: "/dashboard",
    });
  } else {
    setError(res.data);
    setSubmitting(false);
  }
};
