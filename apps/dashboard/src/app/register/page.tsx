import { redirect } from "next/navigation";
import type User from "types/user";
import { readUserByEmail } from "@/services/user";
import RegisterClient from "./client";

const Register = async () => {
  const user: User | null = await readUserByEmail();

  if (user) {
    redirect("/dashboard");
  }

  return <RegisterClient />;
};

export default Register;
