import { redirect } from "next/navigation";
import type { Post, User } from "types";
import { readAllPosts, readUserByEmail } from "services";
import HomeClient from "./client";

export default async function Home() {
  const data: Post[] = await readAllPosts();
  const user: User | null = await readUserByEmail();

  if (!user) {
    redirect("/auth");
  }

  return <HomeClient data={data} user={user} />;
}
