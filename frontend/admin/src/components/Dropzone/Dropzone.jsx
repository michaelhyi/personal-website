import "./Dropzone.css";

import { useCallback } from "react";
import { useDropzone } from "react-dropzone";
import AiOutlineClose from "../Icons/AiOutlineClose";
import AiOutlineCloudDownload from "../Icons/AiOutlineCloudDownload";
import { readPostImage } from "../../services/post";
import Hoverable from "../Hoverable/Hoverable";

export default function Dropzone({
    id,
    showImage,
    setShowImage,
    title,
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

    const { getRootProps, getInputProps } = useDropzone({
        accept: {
            "image/webp": [".webp"],
            "image/jpeg": [".jpeg", ".jpg"],
            "image/png": [".png"],
        },
        maxFiles: 1,
        onDrop,
    });

    return (
        <section className="dropzone-wrapper">
            <section
                {...getRootProps({ className: "dropzone" })}
                className="dropzone-input"
            >
                <input
                    {...getInputProps({ name: "file" })}
                    disabled={submitting}
                />
                <AiOutlineCloudDownload size={48} />
                <p className="dropzone-input-text">
                    Drag & drop an image here, or click to select an image.
                </p>
            </section>
            {showImage && (image !== null || id !== null) ? (
                <>
                    <div className="dropzone-img-card">
                        <img
                            alt="post"
                            className="dropzone-img"
                            src={
                                !image && id
                                    ? readPostImage(id)
                                    : URL.createObjectURL(image)
                            }
                        />
                        <Hoverable onClick={handleImageDelete}>
                            <AiOutlineClose />
                        </Hoverable>
                    </div>
                    <p className="dropzone-img-name">
                        {!image ? `${title}.jpg` : image.name}
                    </p>
                </>
            ) : null}
        </section>
    );
}
