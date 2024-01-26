"use client";

export const dynamic = "force-dynamic";

import Link from "next/link";
import { signOut } from "next-auth/react";
import { useCallback, useEffect, useState } from "react";
import { FaPlus } from "react-icons/fa";
import { FaArrowLeftLong } from "react-icons/fa6";
import Container from "@/components/Container";
import DeleteModal from "@/components/DeleteModal";
import Hoverable from "@/components/Hoverable";
import Loading from "@/components/Loading";
import PostCard from "@/components/PostCard";
import { authenticate, validateToken } from "@/services/auth";
import type { Post } from "@/types/post";
import type { User } from "@/types/user";

export default function BlogClient({
  user,
  data,
}: Readonly<{
  user: User;
  data: Post[];
}>) {
  const [id, setId] = useState<string | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [modalOpen, setModalOpen] = useState<boolean>(false);
  const [menuOpen, setMenuOpen] = useState<boolean[]>(
    new Array(data.length).fill(false)
  );

  const handleToggleMenu = useCallback(
    (index: number) => {
      setMenuOpen(
        menuOpen.map((v, i) => {
          if (i === index) return !v;
          return false;
        })
      );
    },
    [setMenuOpen, menuOpen]
  );

  const handleToggleModal = useCallback(
    (postId?: string | null) => {
      setMenuOpen(new Array(data.length).fill(false));

      if (modalOpen) {
        setModalOpen(false);
      } else if (postId) {
        setModalOpen(true);
        setId(postId);
      }
    },
    [setMenuOpen, data.length, modalOpen, setModalOpen, setId]
  );

  const handleLogout = useCallback(async () => {
    localStorage.removeItem("token");
    await signOut();
  }, []);

  useEffect(() => {
    let token: string | null = localStorage.getItem("token");

    if (!token) {
      void (async () => {
        token = await authenticate(user.username);
        localStorage.setItem("token", token);
        setLoading(false);
      })();
    } else {
      void (async () => {
        try {
          const validToken = await validateToken(token);

          if (!validToken) {
            localStorage.removeItem("token");
            await signOut();
          } else {
            setLoading(false);
          }
        } catch {
          localStorage.removeItem("token");
          await signOut();
        }
      })();
    }
  }, [user.username]);

  if (loading) {
    return <Loading />;
  }

  return (
    <Container absoluteFooter>
      <Hoverable>
        <button
          type="button"
          onClick={handleLogout}
          className="flex items-center gap-2 text-xs text-neutral-300"
        >
          <FaArrowLeftLong /> Logout
        </button>
      </Hoverable>
      <div className="mt-8 font-bold text-3xl">Blog</div>
      <div className="mt-2 text-sm font-medium text-neutral-400">
        An exploration of my enthusiasm for cinema through reviews, analyses,
        and essays on various films.
      </div>
      <div className="mt-8 flex flex-col gap-4">
        <Link
          href="/blog?mode=create"
          className="flex 
                     items-center 
                     gap-2 
                     ml-auto
                     focus:outline-none 
                     text-xs 
                     bg-black
                    text-white 
                      border-[1px] 
                      border-neutral-500 
                      font-semibold 
                      px-3 
                      py-2 
                      rounded-md 
                      shadow-md
                      cursor-pointer
                      mb-8
                      duration-500 
                      hover:opacity-50"
        >
          <FaPlus />
          Create Post
        </Link>
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
