export default function Hoverable({ children, className }) {
  return (
    <div
      className={`${className} cursor-pointer duration-300 hover:opacity-50`}
    >
      {children}
    </div>
  );
}
