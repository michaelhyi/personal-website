import { readUserByToken } from "../../../../services/auth";
import { readPost } from "../../../../services/post";
import User from "../../../../types/dto/User";
import Client from "./client";

interface IParams {
  params: { id: string };
}

const Edit: React.FC<IParams> = async ({ params }) => {
  const { id } = params;
  const token = await localStorage.getItem("token");

  let authorized = false,
    data = null;

  if (token) {
    const res = await readUserByToken(token);

    if (res.status === 200) {
      const user: User = await res.json();
      if (user.authorities[0].authority.includes("ROLE_ADMIN")) {
        authorized = true;
      }
    }
  }

  if (id) {
    const res = await readPost(id);
    data = await res.json();
  }

  return <Client id={id} authorized={authorized} data={data} />;
};

export default Edit;
