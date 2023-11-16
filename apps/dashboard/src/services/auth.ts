import axios from "axios";
import type { FieldValues } from "react-hook-form";

export const auth = async (type: string, data: FieldValues) => {
  const res = await axios.post(
    `${process.env.NEXT_PUBLIC_API_URL}/auth/${type}`,
    data,
  );

  localStorage.setItem("token", res.data as string);
};
