import "./blog.css";

import { useCallback, useEffect, useState } from "react";

import IoEllipsisHorizontal from "../../components/Icons/IoEllipsisHorizontal";

import {
    AuthorizedRoute,
    BlogHeader,
    Container,
    DeleteModal,
    Hoverable,
    Loading,
    Menu,
} from "../../components";
import { readAllPosts } from "../../services/post";

export default function Blog() {
    const [id, setId] = useState(null);
    const [menuOpen, setMenuOpen] = useState(null);
    const [modal, setModal] = useState({
        visible: false,
    });

    const [query, setQuery] = useState({
        data: null,
        loading: true,
        error: false,
    });
    const { data, loading } = query;

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

            setModal({
                visible: !modal.visible,
            });

            if (postId) {
                setId(postId);
            }
        },
        [setMenuOpen, data, modal.visible, setModal, setId],
    );

    useEffect(() => {
        (async () => {
            try {
                const posts = await readAllPosts();
                setMenuOpen(new Array(posts.length).fill(false));
                setQuery({ data: posts, loading: false, error: false });
            } catch (e) {
                setQuery({ data: null, loading: false, error: true });
            }
        })();
    }, []);

    if (loading) return <Loading />;

    return (
        <AuthorizedRoute>
            <Container absoluteFooter>
                <BlogHeader />
                <section className="blog-body">
                    {data &&
                        menuOpen &&
                        data.map((post, index) => (
                            <div key={post.id} className="blog-post-card">
                                <Hoverable>
                                    <a
                                        className="blog-post-title"
                                        href={`${process.env.REACT_APP_WEB_URL}/blog/${post.id}`}
                                    >
                                        {post.title}
                                    </a>
                                </Hoverable>
                                <section className="blog-post-menu">
                                    <Hoverable
                                        onClick={() => {
                                            toggleMenu(index);
                                        }}
                                    >
                                        <IoEllipsisHorizontal />
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
                    modalOpen={modal.visible}
                    handleToggleModal={toggleModal}
                />
            </Container>
        </AuthorizedRoute>
    );
}
