import axios from "axios";
import { setCookie } from "cookies-next";
import type { FieldValues } from "react-hook-form";

export const auth = async (type: string, data: FieldValues) => {
  const res = await axios.post(
    `${process.env.NEXT_PUBLIC_API_URL}/auth/${type}`,
    data,
  );

  setCookie("token", res.data as string, { maxAge: 86400 });
};
