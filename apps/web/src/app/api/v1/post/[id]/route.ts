import prisma from "@/lib/prisma";

interface Params {
  id: string;
}

export async function GET(_: Request, {params}: {params: Params}) {
  const {id} = params;

  const post = await prisma.post.findUnique({where: {id: parseInt(id)}});

  if (post) {
    return Response.json(post);
  }

  return new Response("", {status: 404})

}