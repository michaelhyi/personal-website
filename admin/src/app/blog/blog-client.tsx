"use client";

import Container from "@/components/Container";
import DeleteModal from "@/components/DeleteModal";
import Hoverable from "@/components/Hoverable";
import Loading from "@/components/Loading";
import Menu from "@/components/Menu";
import { authenticate, validateToken } from "@/services/auth";
import type { Post } from "@/types/post";
import type { User } from "@/types/user";
import { signOut } from "next-auth/react";
import Link from "next/link";
import { useCallback, useEffect, useState } from "react";
import { FaPlus } from "react-icons/fa";
import { FaArrowLeftLong } from "react-icons/fa6";
import { FiArrowUpRight } from "react-icons/fi";
import { IoEllipsisHorizontal } from "react-icons/io5";

//TODO: refactor

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
      <div className="flex items-center justify-between">
        <button
          type="button"
          onClick={handleLogout}
          className="text-xs text-neutral-300"
        >
          <Hoverable className="flex items-center gap-2">
            <FaArrowLeftLong /> Logout
          </Hoverable>
        </button>
        <Link
          href="/blog?mode=create"
          className="focus:outline-none 
                     text-xs 
                   bg-black
                   text-white 
                     border-[1px] 
                   border-white 
                     font-semibold 
                     px-3 
                     py-2 
                     rounded-md 
                     shadow-md"
        >
          <Hoverable className="flex items-center gap-2">
            <FaPlus />
            Create Post
          </Hoverable>
        </Link>
      </div>
      <div className="mt-8 flex flex-col gap-2">
        {data.map((post, index) => (
          <div key={post.id} className="flex justify-between">
            <Hoverable>
              <Link
                className="flex text-sm font-medium"
                href={`${process.env.NEXT_PUBLIC_WEB_URL}/blog/${post.id}`}
              >
                {post.title} <FiArrowUpRight />
              </Link>
            </Hoverable>
            <div className="relative">
              <Hoverable>
                <IoEllipsisHorizontal
                  onClick={() => {
                    handleToggleMenu(index);
                  }}
                />
              </Hoverable>
              {menuOpen[index] ? (
                <Menu id={post.id} handleToggleModal={handleToggleModal} />
              ) : null}
            </div>
          </div>
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
