import { readPost } from "@/services/post";

interface Params {
  id: string;
}
export async function GET(_: Request, {params}: {params: Params}) {
  const {id} = params;

  const post = await readPost(id);

  if (!post) {
    return new Response("", {status: 404});
  } 
  
  return Response.json(post);
}