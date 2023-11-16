import axios from "axios";
import type User from "@/types/user";

export const readUserByToken = async (): Promise<User | null> => {
  const res = await axios(
    `${process.env.NEXT_PUBLIC_API_URL}/user/${localStorage.getItem("token")}`,
  );

  return res.data as User;
};
