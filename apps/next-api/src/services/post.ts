import prisma from "../libs/prisma";

export const readPost = (id: string) => {
  return prisma.post.findUnique({
    where: { id: parseInt(id) },
  });
};

export const readAllPosts = () => {
  return prisma.post.findMany();
};
