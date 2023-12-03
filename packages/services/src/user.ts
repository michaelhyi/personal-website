import axios from "axios";
import { getServerSession } from "next-auth";
import { authOptions } from "../../../apps/dashboard/pages/api/auth/[...nextauth]";
import type { User } from "types";

export const readUserByEmail = async (): Promise<User | null> => {
  const session = await getServerSession(authOptions);

  if (!session?.user?.email) {
    return null;
  }

  const res = await axios(
    `${process.env.NEXT_PUBLIC_API_URL}/user/${session.user.email}`,
  );

  return res.status === 200 ? await res.data : null;
};