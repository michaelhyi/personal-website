import axios from "axios";
import { getServerSession } from "next-auth";
import { authOptions } from "../../pages/api/auth/[...nextauth]";
import type { User } from "@/types/user";

export const readUserByEmail = async (): Promise<User | null> => {
  const session = await getServerSession(authOptions);

  if (!session?.user?.email) {
    return null;
  }

  try {
    const { data } = await axios(
      `${process.env.NEXT_PUBLIC_API_URL}/user/${session.user.email}`
    );

    return data;
  } catch {
    return null;
  }
};
