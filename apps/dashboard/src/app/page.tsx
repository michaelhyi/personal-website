import { redirect } from "next/navigation";
import { readUserByEmail } from "@/services/user";
import LoginClient from "./client";

const Home = async () => {
  const user = await readUserByEmail();

  if (user) {
    redirect("/dashboard");
  }

  return <LoginClient />;
};

export default Home;
