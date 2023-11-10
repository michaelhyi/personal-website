import prisma from "@/lib/prisma";

export const readPost = async (id: string) => {
  const post = await prisma.post.findUnique({ where: { id: parseInt(id) } });
  return post;
};

export const readAllPosts = async () => {
  const posts = await prisma.post.findMany({ orderBy: { date: "desc" } });
  return posts;
};
