import { readUserByToken } from "@/services/user";
import LoginClient from "./client";

const Home = async () => {
  const user = await readUserByToken();

  return <LoginClient user={user} />;
};

export default Home;
