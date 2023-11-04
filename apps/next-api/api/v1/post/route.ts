// import type { NextApiRequest, NextApiResponse } from "next";
// import type Post from "@/app/types/dto/post";
// import prisma from "@/libs/prisma";

// export async function POST(req: NextApiRequest, res: NextApiResponse) {
//   const {title, description, body} = req.body;

//   const post: Post = await prisma.post.create({
//     data: {
//       title,
//       description,
//       body
//     }
//   });

//   res.status(200).json(post.id);
// }

// export async function GET(_req: NextApiRequest, res: NextApiResponse) {
//   const posts: Post[] = await prisma.post.findMany();
  
//   res.status(200).json(posts);
// }