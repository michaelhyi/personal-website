import { readUserByToken } from "@/services/user";
import RegisterClient from "./client";

const Register = async () => {
  const user = await readUserByToken();

  return <RegisterClient user={user} />;
};

export default Register;
