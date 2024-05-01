import { useCallback } from "react";
import { useDropzone } from "react-dropzone";
import { AiOutlineClose, AiOutlineCloudDownload } from "react-icons/ai";
import { readPostImage } from "../services/post";

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
        <section className="flex flex-col mt-2">
            <section
                {...getRootProps({ className: "dropzone" })}
                className="flex flex-col items-center justify-center border-[1px] border-neutral-600 border-dashed cursor-pointer duration-500 hover:opacity-50 rounded-lg shadow-lg h-64"
            >
                <input
                    {...getInputProps({ name: "file" })}
                    disabled={submitting}
                />
                <AiOutlineCloudDownload size={48} />
                <p className="text-xs text-neutral-300">
                    Drag & drop an image here, or click to select an image.
                </p>
            </section>
            {showImage && (image !== null || id !== null) ? (
                <>
                    <div className="flex gap-2 mt-4">
                        <img
                            alt="post"
                            className="h-[150px]"
                            src={
                                !image && id
                                    ? readPostImage(id)
                                    : URL.createObjectURL(image)
                            }
                        />
                        <AiOutlineClose
                            size={12}
                            onClick={handleImageDelete}
                            className="cursor-pointer duration-500 hover:opacity-50"
                        />
                    </div>
                    <p className="text-xs mt-2">
                        {!image ? `${title}.jpg` : image.name}
                    </p>
                </>
            ) : null}
        </section>
    );
}
