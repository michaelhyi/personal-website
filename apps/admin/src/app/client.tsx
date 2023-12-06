"use client";

import type { Post, User } from "types";
import { Container } from "ui";
import { useEffect } from "react";
import { authenticate } from "services";
import PostCard from "@/components/PostCard";

export default function HomeClient({
  data,
  user,
}: {
  data: Post[];
  user: User;
}) {
  useEffect(() => {
    let token: string | null = localStorage.getItem("token");

    if (!token) {
      void (async () => {
        token = await authenticate(user.username);
        localStorage.setItem("token", token);
      })();
    }
  }, [user.username]);

  return (
    <Container>
      <div className="flex flex-col gap-4">
        {data.map((post) => (
          <PostCard key={post.id} data={post} />
        ))}
      </div>
    </Container>
  );
}
