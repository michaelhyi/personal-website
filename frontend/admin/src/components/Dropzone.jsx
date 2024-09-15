import "../css/dropzone.css";
import { useCallback } from "react";
import AiOutlineClose from "./Icons/AiOutlineClose";
import AiOutlineCloudDownload from "./Icons/AiOutlineCloudDownload";

export default function Dropzone({
    id,
    showImage,
    setShowImage,
    title,
    postImage,
    submitting,
    image,
    setImage,
}) {
    const onDrop = useCallback(
        (acceptedFiles) => {
            setImage(acceptedFiles[0]);
            setShowImage(true);
        },
        [setImage, setShowImage],
    );

    const handleImageDelete = useCallback(() => {
        setImage(null);
        setShowImage(false);
    }, [setImage, setShowImage]);

    return (
        <section className="dropzone-wrapper">
            <section className="dropzone">
                <input
                    type="file"
                    className="dropzone-input"
                    onChange={(e) => onDrop(e.target.files)}
                    disabled={submitting}
                />
                <div className="dropzone-input-content">
                    <AiOutlineCloudDownload />
                    <p className="dropzone-input-text">
                        Drag & drop an image here, or click to select an image.
                    </p>
                </div>
            </section>
            {showImage && (image !== null || id !== null) ? (
                <>
                    <div className="dropzone-img-card">
                        <img
                            alt="post"
                            className="dropzone-img"
                            src={
                                !image && id && postImage
                                    ? `data:image/png;base64,${postImage}`
                                    : URL.createObjectURL(image)
                            }
                        />
                        <button
                            aria-label="delete image"
                            type="button"
                            onClick={handleImageDelete}
                        >
                            <AiOutlineClose />
                        </button>
                    </div>
                    <p className="dropzone-img-name">
                        {!image ? `${title}.jpg` : image.name}
                    </p>
                </>
            ) : null}
        </section>
    );
}
