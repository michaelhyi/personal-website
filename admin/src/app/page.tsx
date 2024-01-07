import { redirect } from "next/navigation";
import { readUserByEmail } from "@/services/user";
import type { User } from "@/types/user";
import AuthClient from "./client";

export default async function Auth() {
  const user: User | null = await readUserByEmail();

  if (user) {
    redirect("/blog");
  }

  return <AuthClient />;
}
