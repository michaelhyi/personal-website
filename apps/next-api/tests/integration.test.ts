import request from "supertest";
import prisma from "../src/libs/prisma";

afterEach(async () => {
  await prisma.post.deleteMany();
});

test("[INTEGRATION] GET /api/v1/post/{id}", async () => {
  const post = await prisma.post.create({
    data: {
      title: "title",
      desc: "desc",
      body: "body",
    },
  });

  let res = await request("http://localhost:3001/").get(
    `api/v1/post/${post.id}`,
  );

  expect(res.status).toEqual(200);
  expect(res.body.id).toEqual(post.id);
  expect(res.body.date).toEqual(post.date.toISOString());
  expect(res.body.title).toEqual(post.title);
  expect(res.body.desc).toEqual(post.desc);
  expect(res.body.body).toEqual(post.body);

  res = await request("http://localhost:3001/").get("api/v1/post/-1");

  expect(res.status).toEqual(404);
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

  const { status, body } = await request("http://localhost:3001/").get(
    "api/v1/post",
  );

  expect(status).toEqual(200);
  expect(Array.isArray(body)).toBeTruthy();
  expect(body.length).toEqual(2);

  expect(body[0].id).toEqual(firstPost.id);
  expect(body[0].date).toEqual(firstPost.date.toISOString());
  expect(body[0].title).toEqual(firstPost.title);
  expect(body[0].desc).toEqual(firstPost.desc);
  expect(body[0].body).toEqual(firstPost.body);

  expect(body[1].id).toEqual(secondPost.id);
  expect(body[1].date).toEqual(secondPost.date.toISOString());
  expect(body[1].title).toEqual(secondPost.title);
  expect(body[1].desc).toEqual(secondPost.desc);
  expect(body[1].body).toEqual(secondPost.body);
});
