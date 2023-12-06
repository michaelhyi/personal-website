"use client";

import { useForm } from "react-hook-form";

export default function PostClient({
  data,
}: {
  data: {
    title: string;
    image: FormData;
    body: string;
  };
}) {
  useForm({
    defaultValues: {
      title: data.title,
      image: data.image,
      body: data.body,
    },
  });

  return <div>hi</div>;
}
