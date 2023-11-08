import { readAllPosts } from "@/services/post";

export async function GET(_: Request) {
  return Response.json(await readAllPosts());
}