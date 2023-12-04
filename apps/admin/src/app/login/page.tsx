import { redirect } from "next/navigation";
import type { User } from "types";
import { readUserByEmail } from "services";
import LoginClient from "./client";

const Login = async () => {
  const user: User | null = await readUserByEmail();

  if (user) {
    redirect("/dashboard");
  }

  return <LoginClient />;
};

export default Login;
