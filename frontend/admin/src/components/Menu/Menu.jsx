import "./Menu.css";

import IoPencil from "../Icons/IoPencil";
import IoTrashOutline from "../Icons/IoTrashOutline";
import Hoverable from "../Hoverable/Hoverable";

export default function Menu({ id, handleToggleModal }) {
    return (
        <aside className="menu-wrapper">
            <Hoverable>
                <a href={`/blog/post?id=${id}`} className="menu-btn">
                    <IoPencil /> Edit Post
                </a>
            </Hoverable>
            <Hoverable>
                <button
                    onClick={() => {
                        handleToggleModal(id);
                    }}
                    type="button"
                    className="menu-btn"
                >
                    <IoTrashOutline />
                    Delete Post
                </button>
            </Hoverable>
        </aside>
    );
}
