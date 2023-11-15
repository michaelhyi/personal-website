import { FaRegTrashAlt, FaPencilAlt } from "react-icons/fa";
import Container from "@/components/Container";

const Blog = () => {
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
                <th className="px-6 py-4">Rating</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td className="px-6 py-4">1</td>
                <td className="px-6 py-4">11/09/2023</td>
                <td className="px-6 py-4">No Country For Old Men</td>
                <td className="px-6 py-4">10</td>
                <td className="pl-6 py-4">
                  <button
                    className="bg-blue-400 p-2 rounded-md duration-500 hover:opacity-50"
                    type="button"
                  >
                    <FaPencilAlt />
                  </button>
                </td>
                <div className="px-2" />
                <td className="pr-3 py-2">
                  <button
                    className="bg-red-400 p-2 rounded-md duration-500 hover:opacity-50"
                    type="button"
                  >
                    <FaRegTrashAlt />
                  </button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </Container>
  );
};

export default Blog;
