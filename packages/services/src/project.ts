import type { FieldValues } from "react-hook-form";
import type { Project } from "types";
import qs from "query-string";

export const createProject = async (data: FieldValues): Promise<number> => {
  const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/project`, {
    body: JSON.stringify(data),
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("token")}`,
    },
  });

  return await res.json();
};

export const readProject = async (id: string): Promise<Project | null> => {
  const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/project/${id}`);

  return res.ok ? await res.json() : null;
};

export const readAllProjects = async (): Promise<Project[]> => {
  const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/project`);
  return await res.json();
};

export const updateProject = async (
  id: string,
  data: FieldValues,
): Promise<void> => {
  await fetch(
    `${process.env.NEXT_PUBLIC_API_URL}/project/${id}?${qs.stringify(data)}`,
    {
      method: "PUT",
      headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
    },
  );
};

export const deleteProject = async (id: number): Promise<void> => {
  await fetch(`${process.env.NEXT_PUBLIC_API_URL}/project/${id}`, {
    method: "DELETE",
    headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
  });
};
