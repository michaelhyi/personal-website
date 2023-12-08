"use client";

import type { Post, User } from "@personal-website/types";
import { Container } from "@personal-website/ui";
import { useCallback, useEffect, useState } from "react";
import { authenticate } from "@personal-website/services";
import PostCard from "@/components/PostCard";
import DeleteModal from "@/components/DeleteModal";

export default function HomeClient({
  data,
  user,
}: {
  data: Post[];
  user: User;
}) {
  const [id, setId] = useState<number | null>(null);
  const [modalOpen, setModalOpen] = useState<boolean>(false);
  const [menuOpen, setMenuOpen] = useState<boolean[]>(
    new Array(data.length).fill(false),
  );

  const handleToggleMenu = useCallback(
    (index: number) => {
      setMenuOpen(
        menuOpen.map((v, i) => {
          if (i === index) return !v;
          return false;
        }),
      );
    },
    [setMenuOpen, menuOpen],
  );

  const handleToggleModal = useCallback(
    (postId?: number | null) => {
      setMenuOpen(new Array(data.length).fill(false));

      if (modalOpen) {
        setModalOpen(false);
      } else if (postId) {
        setModalOpen(true);
        setId(postId);
      }
    },
    [setMenuOpen, data.length, modalOpen, setModalOpen, setId],
  );

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
        {data.map((post, index) => (
          <PostCard
            key={post.id}
            data={post}
            index={index}
            menuOpen={menuOpen[index]}
            handleToggleMenu={handleToggleMenu}
            handleToggleModal={handleToggleModal}
          />
        ))}
      </div>
      {modalOpen && id ? (
        <DeleteModal
          id={id}
          modalOpen={modalOpen}
          handleToggleModal={handleToggleModal}
        />
      ) : null}
    </Container>
  );
}
