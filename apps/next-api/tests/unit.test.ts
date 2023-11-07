import prisma from "../src/libs/prisma";
import { readAllPosts } from "../src/services/post";

afterEach(async () => {
  await prisma.post.deleteMany();
});

test("[UNIT] readPost", async () => {
  const post = await prisma.post.create({
    data: {
      title: "title",
      desc: "desc",
      body: "body",
    },
  });

  let res = await prisma.post.findUnique({
    where: { id: post.id },
  });

  if (res) {
    expect(res.id).toEqual(post.id);
    expect(res.date).toEqual(post.date);
    expect(res.title).toEqual(post.title);
    expect(res.desc).toEqual(post.desc);
    expect(res.body).toEqual(post.body);
  }

  res = await prisma.post.findUnique({ where: { id: -1 } });

  expect(res).toEqual(null);
});

test("[UNIT] readAllPosts", async () => {
  const firstPost = await prisma.post.create({
    data: {
      title: "title",
      desc: "desc",
      body: "body",
    },
  });

  const secondPost = await prisma.post.create({
    data: {
      title: "title2",
      desc: "desc2",
      body: "body2",
    },
  });

  const posts = await readAllPosts();

  expect(Array.isArray(posts)).toBeTruthy();
  expect(posts.length).toEqual(2);

  expect(posts[0].id).toEqual(firstPost.id);
  expect(posts[0].date).toEqual(firstPost.date);
  expect(posts[0].title).toEqual(firstPost.title);
  expect(posts[0].desc).toEqual(firstPost.desc);
  expect(posts[0].body).toEqual(firstPost.body);

  expect(posts[1].id).toEqual(secondPost.id);
  expect(posts[1].date).toEqual(secondPost.date);
  expect(posts[1].title).toEqual(secondPost.title);
  expect(posts[1].desc).toEqual(secondPost.desc);
  expect(posts[1].body).toEqual(secondPost.body);
});
