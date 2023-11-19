import axios from "axios";
import type { FieldValues } from "react-hook-form";
import type { Project } from "types";
import qs from "query-string";

export const createProject = async (data: FieldValues): Promise<number> => {
  const res = await axios.post(
    `${process.env.NEXT_PUBLIC_API_URL}/project`,
    data,
    {
      headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
    },
  );

  return res.data;
};

export const readProject = async (id: string): Promise<Project | null> => {
  const res = await axios(`${process.env.NEXT_PUBLIC_API_URL}/project/${id}`);

  return res.status === 200 ? res.data : null;
};

export const readAllProjects = async (): Promise<Project[]> => {
  const res = await axios(`${process.env.NEXT_PUBLIC_API_URL}/project`);
  return res.data;
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
  await axios.delete(`${process.env.NEXT_PUBLIC_API_URL}/project/${id}`, {
    headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
  });
};
