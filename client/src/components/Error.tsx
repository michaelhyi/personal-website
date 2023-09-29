interface Props {
  text: string;
}

const Error: React.FC<Props> = ({ text }) => {
  return <div className="mt-4 text-red-500">{text}</div>;
};

export default Error;
