import { useCallback, useEffect, useState } from "react";
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

    const [data, setData] = useState(null);
    const [loading, setLoading] = useState(true);

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

    useEffect(() => {
        (async () => {
            const posts = await readAllPosts();

            setData(posts);
            setMenuOpen(new Array(posts.length).fill(false));
            setLoading(false);
        })();
    }, []);

    if (loading) return <Loading />;

    return (
        <AuthorizedRoute>
            <Container absoluteFooter>
                <BlogHeader />
                <div className="mt-8 flex flex-col gap-2">
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
                            <div className="relative">
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
                            </div>
                        </div>
                    ))}
                </div>
                <DeleteModal
                    id={id}
                    modalOpen={modalOpen}
                    handleToggleModal={toggleModal}
                />
            </Container>
        </AuthorizedRoute>
    );
}
