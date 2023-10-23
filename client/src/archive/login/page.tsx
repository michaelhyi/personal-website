import { readUserByToken } from "../../services/auth";
import Client from "./client";

const Login = async () => {
  const token = await localStorage.getItem("token");
  let authenticated = false;
  if (token) {
    const { status } = await readUserByToken(token);
    if (status === 200) authenticated = true;
  }

  return <Client authenticated={authenticated} />;
};

export default Login;
