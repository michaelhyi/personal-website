import { useQuery } from "@tanstack/react-query";
import { useCallback, useState } from "react";
import { FiArrowUpRight } from "react-icons/fi";
import { IoEllipsisHorizontal } from "react-icons/io5";

import AuthorizedRoute from "../components/AuthorizedRoute";
import BlogHeader from "../components/BlogHeader";
import Container from "../components/Container";
import DeleteModal from "../components/DeleteModal";
import Hoverable from "../components/Hoverable";
import Loading from "../components/Loading";
import Menu from "../components/Menu";

import { readAllPosts } from "../services/post";

export default function Blog() {
    const [id, setId] = useState(null);
    const [menuOpen, setMenuOpen] = useState(null);
    const [modalOpen, setModalOpen] = useState(false);

    const { data, isLoading } = useQuery({
        queryKey: ["readAllPosts"],
        queryFn: async () => {
            const posts = await readAllPosts();
            setMenuOpen(new Array(posts.length).fill(false));
            return posts;
        },
    });

    const toggleMenu = useCallback(
        (index) => {
            setMenuOpen(
                menuOpen && menuOpen.map((v, i) => (i === index ? !v : false)),
            );
        },
        [setMenuOpen, menuOpen],
    );

    const toggleModal = useCallback(
        (postId) => {
            setMenuOpen(data && new Array(data.length).fill(false));
            setModalOpen(!modalOpen);

            if (postId) {
                setId(postId);
            }
        },
        [setMenuOpen, data, modalOpen, setModalOpen, setId],
    );

    if (isLoading) return <Loading />;

    return (
        <AuthorizedRoute>
            <Container absoluteFooter>
                <BlogHeader />
                <section className="mt-8 flex flex-col gap-2">
                    {data.map((post, index) => (
                        <div key={post.id} className="flex justify-between">
                            <Hoverable>
                                <a
                                    className="flex text-sm font-medium"
                                    href={`${process.env.REACT_APP_WEB_URL}/blog/${post.id}`}
                                >
                                    {post.title} <FiArrowUpRight />
                                </a>
                            </Hoverable>
                            <section className="relative">
                                <Hoverable>
                                    <IoEllipsisHorizontal
                                        onClick={() => {
                                            toggleMenu(index);
                                        }}
                                    />
                                </Hoverable>
                                {menuOpen[index] ? (
                                    <Menu
                                        id={post.id}
                                        handleToggleModal={toggleModal}
                                    />
                                ) : null}
                            </section>
                        </div>
                    ))}
                </section>
                <DeleteModal
                    id={id}
                    modalOpen={modalOpen}
                    handleToggleModal={toggleModal}
                />
            </Container>
        </AuthorizedRoute>
    );
}
