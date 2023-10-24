import { readUserByToken } from "@/services/auth";
import { readPost } from "@/services/post";
import { FC } from "react";
import Client from "./client";

interface IParams {
   params: { id: string };
}

const View: FC<IParams> = async ({ params }) => {
   const { id } = params;

   let data = null,
      admin = false;

   if (id) {
      let res = await readPost(id);

      if (res.status === 200) {
         data = await res.json();

         const token = await localStorage.getItem("token");

         if (token) res = await readUserByToken(token);
         if (token && res.status === 200) {
            const user = await res.json();

            if (user.authorities[0].authority.includes("ROLE_ADMIN"))
               admin = true;
         }
      }
   }

   return <Client id={id} data={data} admin={admin} />;
};

export default View;
