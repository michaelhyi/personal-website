import { redirect } from "next/navigation";
import { readUserByEmail } from "@/services/user";
import RegisterClient from "./client";

const Register = async () => {
  const user = await readUserByEmail();

  if (user) {
    redirect("/dashboard");
  }

  return <RegisterClient />;
};

export default Register;
