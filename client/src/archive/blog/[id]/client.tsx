"use client";

import Container from "@/components/Container";
import ArrowLink from "@/components/links/ArrowLink";
import Post from "@/types/dto/Post";
import { format } from "date-fns";
import { notFound, useRouter } from "next/navigation";
import { FC, useEffect } from "react";
import { BsFillPencilFill } from "react-icons/bs";

interface Props {
   id: string;
   data: Post;
   admin: boolean;
}

const Client: FC<Props> = ({ id, data, admin }) => {
   const router = useRouter();

   useEffect(() => {
      if (!id || !data) notFound();
   }, [id, data]);

   return (
      <Container>
         <div className="mt-24" />
         <div className="flex justify-between">
            <ArrowLink href="/blog" left text="Blog" />
            {admin && (
               <button
                  className="duration-500 hover:opacity-50"
                  onClick={() => router.push("/blog/edit/" + id)}
               >
                  <BsFillPencilFill color="#f9a8d4" />
               </button>
            )}
         </div>
         <div className="mt-12 text-2xl font-semibold">{data!.title}</div>
         <div className="mt-6 text-xs opacity-75">
            {format(new Date(data!.date), "PPP")}
         </div>
         <div
            className="mt-8"
            dangerouslySetInnerHTML={{
               __html: data!.body,
            }}
         />
      </Container>
   );
};

export default Client;
