import { redirect } from "next/navigation";
import type { User } from "types";
import { readUserByEmail } from "services";
import AuthClient from "./client";

export default async function Auth() {
  const user: User | null = await readUserByEmail();

  if (user) {
    redirect("/");
  }

  return <AuthClient />;
}
