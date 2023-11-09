import type { FC } from "react";
import { readPost } from "@/services/post";
import Client from "./client";

interface Params {
  params: { id: string };
}

const View: FC<Params> = async ({ params }) => {
  const { id } = params;
  const data = await readPost(id);

  return <Client data={data} />;
};

export default View;
