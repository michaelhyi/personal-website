import { useCallback, useEffect, useState } from "react";
import { FaPlus } from "react-icons/fa";
import { FaArrowLeftLong } from "react-icons/fa6";
import { FiArrowUpRight } from "react-icons/fi";
import { IoEllipsisHorizontal } from "react-icons/io5";
import { useNavigate } from "react-router-dom";
import Container from "../components/Container";
import DeleteModal from "../components/DeleteModal";
import Hoverable from "../components/Hoverable";
import Loading from "../components/Loading";
import Menu from "../components/Menu";
import { validateToken } from "../services/auth";
import { readAllPosts } from "../services/post";

export default function Blog() {
  const navigate = useNavigate();
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [id, setId] = useState(null);
  const [modalOpen, setModalOpen] = useState(false);
  const [menuOpen, setMenuOpen] = useState(null);

  const logout = useCallback(() => {
    localStorage.removeItem("token");
    navigate("/");
  }, [navigate]);

  const toggleMenu = useCallback(
    (index) => {
      setMenuOpen(
        menuOpen !== null &&
          menuOpen.map((v, i) => {
            if (i === index) return !v;
            return false;
          }),
      );
    },
    [setMenuOpen, menuOpen],
  );

  const toggleModal = useCallback(
    (postId) => {
      setMenuOpen(data && new Array(data.length).fill(false));

      if (modalOpen) {
        setModalOpen(false);
      } else if (postId) {
        setModalOpen(true);
        setId(postId);
      }
    },
    [setMenuOpen, data, modalOpen, setModalOpen, setId],
  );

  useEffect(() => {
    (async () => {
      const token = localStorage.getItem("token");

      if (!token) {
        navigate("/");
      } else {
        try {
          await validateToken(token);
          const posts = await readAllPosts();

          setData(posts);
          setMenuOpen(new Array(posts.length).fill(false));
          setLoading(false);
        } catch {
          logout();
        }
      }
    })();
  }, [navigate, logout]);

  if (loading) return <Loading />;

  return (
    <Container absoluteFooter>
      <div className="flex items-center justify-between">
        <button
          type="button"
          onClick={logout}
          className="text-xs text-neutral-300"
        >
          <Hoverable className="flex items-center gap-2">
            <FaArrowLeftLong /> Logout
          </Hoverable>
        </button>
        <a
          href="/blog/post?mode=create"
          className="focus:outline-none 
                     text-xs 
                   bg-neutral-800
                   text-white 
                     font-semibold 
                     px-3 
                     py-2 
                     rounded-md 
                     shadow-sm"
        >
          <Hoverable className="flex items-center gap-2">
            <FaPlus />
            Create Post
          </Hoverable>
        </a>
      </div>
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
                <Menu id={post.id} handleToggleModal={toggleModal} />
              ) : null}
            </div>
          </div>
        ))}
      </div>
      {modalOpen && id ? (
        <DeleteModal
          id={id}
          modalOpen={modalOpen}
          handleToggleModal={toggleModal}
        />
      ) : null}
    </Container>
  );
}
