import Image from "next/image";
import { type Dispatch, type SetStateAction, useCallback } from "react";
import { useDropzone } from "react-dropzone";
import { AiOutlineClose, AiOutlineCloudDownload } from "react-icons/ai";
import { readPostImageUrl } from "@/services/post";

export default function Dropzone({
  id,
  showImage,
  setShowImage,
  title,
  submitting,
  setSubmitting,
  image,
  setImage,
}: {
  id: number | null;
  showImage: boolean;
  setShowImage: Dispatch<SetStateAction<boolean>>;
  title: string | null;
  submitting: boolean;
  setSubmitting: Dispatch<SetStateAction<boolean>>;
  image: File | null;
  setImage: Dispatch<SetStateAction<File | null>>;
}) {
  const onDrop = useCallback(
    (acceptedFiles: File[]) => {
      setSubmitting(true);
      setImage(acceptedFiles[0]);
      setShowImage(true);
      setSubmitting(false);
    },
    [setSubmitting, setImage, setShowImage],
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
    <div className="flex flex-col mt-2">
      <div
        {...getRootProps({ className: "dropzone" })}
        className="flex flex-col items-center justify-center border-[1px] border-neutral-600 border-dashed cursor-pointer duration-500 hover:opacity-50 rounded-lg shadow-lg h-64"
      >
        <input {...getInputProps({ name: "file" })} disabled={submitting} />
        <AiOutlineCloudDownload size={48} />
        <div className="text-xs text-neutral-300">
          Drag & drop an image here, or click to select an image.
        </div>
      </div>
      {showImage && (image !== null || id !== null) ? (
        <div>
          <div className="flex gap-2 mt-4">
            <Image
              width={200}
              height={150}
              src={
                !image && id
                  ? readPostImageUrl(id)
                  : URL.createObjectURL(image!)
              }
              alt="image"
            />
            <AiOutlineClose
              size={12}
              onClick={handleImageDelete}
              className="cursor-pointer duration-500 hover:opacity-50"
            />
          </div>
          <div className="text-xs mt-2">
            {!image ? `${title}.jpg` : image.name}
          </div>
        </div>
      ) : null}
    </div>
  );
}
