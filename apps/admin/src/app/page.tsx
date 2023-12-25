import { readUserByEmail } from "@personal-website/services";
import type { User } from "@personal-website/types";
import { redirect } from "next/navigation";
import AuthClient from "./client";

export default async function Auth() {
  const user: User | null = await readUserByEmail();

  if (user) {
    redirect("/blog");
  }

  return <AuthClient />;
}
