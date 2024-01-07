"use client";

export default function ToastError({
  visible,
  message,
}: {
  visible: boolean;
  message: string;
}) {
  return (
    <div
      className={`${visible ? "animate-enter" : "animate-leave"}  bg-red-400 
  shadow-lg 
  rounded-lg 
  flex 
  justify-center`}
    >
      <div className="px-5 py-3 font-semibold text-sm">{message}</div>
    </div>
  );
}
