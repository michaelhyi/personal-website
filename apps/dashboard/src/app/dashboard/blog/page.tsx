import { format } from "date-fns";
import { FaPlus, FaRegTrashAlt, FaPencilAlt } from "react-icons/fa";
import Container from "@/components/Container";
import { readAllPosts } from "@/services/post";
import type Post from "@/types/post";

const Blog = async () => {
  const data = await readAllPosts();

  return (
    <Container>
      <div className="flex flex-col">
        <div className="font-bold text-3xl">Blog</div>
        <div className="mt-12 border-[1px] rounded-md border-neutral-400 text-neutral-300 mx-auto">
          <table className="text-left">
            <thead className="border-b-[1px]  border-neutral-400">
              <tr>
                <th className="px-6 py-4">ID</th>
                <th className="px-6 py-4">Date</th>
                <th className="px-6 py-4">Title</th>
                <th className="px-4 py-4" />
                <th className="px-4 py-4">
                  <button
                    className="bg-green-500 p-2 rounded-md duration-500 hover:opacity-50"
                    type="button"
                  >
                    <FaPlus />
                  </button>
                </th>
              </tr>
            </thead>
            <tbody>
              {data.map((v: Post) => (
                <tr key={v.id}>
                  <td className="px-6 py-4">{v.id}</td>
                  <td className="px-6 py-4">{format(new Date(v.date), "P")}</td>
                  <td className="px-6 py-4">{v.title}</td>
                  <td className="px-4 py-4">
                    <button
                      className="bg-blue-400 p-2 rounded-md duration-500 hover:opacity-50"
                      type="button"
                    >
                      <FaPencilAlt />
                    </button>
                  </td>
                  <td className="px-4 py-4">
                    <button
                      className="bg-red-400 p-2 rounded-md duration-500 hover:opacity-50"
                      type="button"
                    >
                      <FaRegTrashAlt />
                    </button>
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
