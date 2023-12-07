import { type Dispatch, type SetStateAction, useCallback } from "react";
import { useDropzone } from "react-dropzone";
import { AiOutlineClose, AiOutlineCloudDownload } from "react-icons/ai";

export default function Dropzone({
  submitting,
  setSubmitting,
  image,
  setImage,
}: {
  submitting: boolean;
  setSubmitting: Dispatch<SetStateAction<boolean>>;
  image: File | null;
  setImage: Dispatch<SetStateAction<File | null>>;
}) {
  const onDrop = useCallback(
    (acceptedFiles: File[]) => {
      setSubmitting(true);
      setImage(acceptedFiles[0]);
      setSubmitting(false);
    },
    [setSubmitting, setImage],
  );

  const handleImageDelete = useCallback(() => {
    setImage(null);
  }, [setImage]);

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
      {image !== null && (
        <div className="flex gap-2 items-center mt-4">
          <div className="text-xs font-semibold">{image.name}</div>
          <AiOutlineClose
            size={12}
            onClick={handleImageDelete}
            className="cursor-pointer duration-500 hover:opacity-50"
          />
        </div>
      )}
    </div>
  );
}
