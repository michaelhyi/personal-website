// import type { NextApiRequest, NextApiResponse } from "next";
// import prisma from "@/libs/prisma"
// import type Post from "@/app/types/dto/post";

// interface Params {
//   id: string;
// }

// export async function GET(_: NextApiRequest, {params}: {params: Params}, res: NextApiResponse) {
//   const {id} = params;

//   const post: Post | null = await prisma.post.findUnique({where: {id: parseInt(id)}});

//   res.status(200).json(post);
// }

// // export async function PUT() {}
// // export async function DELETE() {}