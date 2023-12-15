"use client";

import { authenticate } from "@personal-website/services";
import type { Post, User } from "@personal-website/types";
import { Container, PostCard } from "@personal-website/ui";
import Link from "next/link";
import { useCallback, useEffect, useState } from "react";
import { FaPlus } from "react-icons/fa";
import DeleteModal from "@/components/DeleteModal";

export default function BlogClient({
  user,
  data,
}: {
  user: User;
  data: Post[];
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
        <Link
          href="/blog?mode=create"
          className="flex 
                     items-center 
                     gap-2 
                     ml-auto
                     focus:outline-none 
                     text-xs 
                     bg-neutral-800
                    text-white 
                      border-[1px] 
                      border-neutral-500 
                      font-semibold 
                      px-3 
                      py-2 
                      rounded-md 
                      shadow-md
                      cursor-pointer
                      duration-500 
                      hover:opacity-50"
        >
          <FaPlus />
          Create Post
        </Link>
        {data.map((post, index) => (
          <PostCard
            key={post.id}
            admin={user.authorities[0].authority === "ROLE_ADMIN"}
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
