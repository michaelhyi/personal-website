import prisma from "../libs/prisma";

export const readPost = async (id: string) => {
  return prisma.post.findUnique({
    where: { id: parseInt(id) },
  });
};

export const readAllPosts = async () => {
  return prisma.post.findMany();
};
