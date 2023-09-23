import Link from "next/link";
import { format } from "date-fns";

interface Props {
  id: number;
  title: string;
  body: string;
  date: string;
}

const PostCard: React.FC<Props> = ({ id, title, body, date }) => {
  return (
    <Link
      className="flex sm:flex-col md:flex-row gap-4 cursor-pointer duration-500 hover:opacity-50"
      href={`/${id}`}
    >
      <div>
        <div className="font-semibold">{title}</div>
        <div className="text-xs mt-2 opacity-75">
          {format(new Date(date), "PPP")}
        </div>
        <div
          className="text-xs mt-2 opacity-75"
          dangerouslySetInnerHTML={{ __html: body }}
        />
      </div>
    </Link>
  );
};

export default PostCard;
