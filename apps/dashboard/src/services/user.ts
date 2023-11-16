import axios from "axios";
import { getServerSession } from "next-auth";
import type User from "@/types/user";
import { authOptions } from "../../pages/api/auth/[...nextauth]";

export const readUserByEmail = async (): Promise<User | null> => {
  const session = await getServerSession(authOptions);

  if (!session?.user?.email) {
    return null;
  }

  try {
    const res = await axios(
      `${process.env.NEXT_PUBLIC_API_URL}/user/${session.user.email}`
    );
    return res.data as User;
  } catch {
    return null;
  }
};
