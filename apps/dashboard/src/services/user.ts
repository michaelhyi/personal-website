import axios from "axios";
import { cookies } from "next/headers";
import type User from "@/types/user";

export const readUserByToken = async (): Promise<User | null> => {
  const token = cookies().get("token");

  if (!token) {
    return null;
  }

  const res = await axios(
    `${process.env.NEXT_PUBLIC_API_URL}/user/${token.value}`,
  );

  return res.data as User;
};
