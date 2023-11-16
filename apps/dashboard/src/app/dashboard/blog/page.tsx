import { format } from "date-fns";
import { FaPlus, FaRegTrashAlt, FaPencilAlt } from "react-icons/fa";
import Link from "next/link";
import Container from "@/components/Container";
import { readAllPosts } from "@/services/post";
import type Post from "@/types/post";

const Blog = async () => {
  const data = await readAllPosts();

  return (
    <Container>
      <div className="flex flex-col">
        <div className="font-bold text-3xl">Blog</div>
        <div className="mt-12 border-[1px] rounded-md border-neutral-500 text-sm mx-auto bg-neutral-800">
          <table className="text-left">
            <thead className="border-b-[1px]  border-neutral-500">
              <tr>
                <th className="px-6 py-4">ID</th>
                <th className="px-6 py-4">Date</th>
                <th className="px-6 pr-96 py-4">Title</th>
                <th className="px-3 py-4" />
                <th className="px-3 py-4">
                  <Link
                    href="/dashboard/blog/create"
                    className="duration-500 hover:opacity-50"
                  >
                    <FaPlus />
                  </Link>
                </th>
              </tr>
            </thead>
            <tbody>
              {data.map((v: Post) => (
                <tr key={v.id}>
                  <td className="px-6 py-4">{v.id}</td>
                  <td className="px-6 py-4">{format(new Date(v.date), "P")}</td>
                  <td className="px-6 pr-96 py-4">{v.title}</td>
                  <td className="px-3 py-4">
                    <Link
                      href={`/dashboard/blog/edit/${v.id}`}
                      className="duration-500 hover:opacity-50 ml-auto"
                    >
                      <FaPencilAlt />
                    </Link>
                  </td>
                  <td className="px-3 py-4">
                    <Link
                      href={`/dashboard/blog/delete/${v.id}`}
                      className="duration-500 hover:opacity-50 ml-auto"
                    >
                      <FaRegTrashAlt />
                    </Link>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </Container>
  );
};

export default Blog;
