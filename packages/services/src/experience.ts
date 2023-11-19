import type { FieldValues } from "react-hook-form";
import type { Experience } from "types";
import qs from "query-string";

export const createExperience = async (data: FieldValues): Promise<number> => {
  const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/experience`, {
    body: JSON.stringify(data),
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("token")}`,
    },
  });

  return await res.json();
};

export const readExperience = async (
  id: string,
): Promise<Experience | null> => {
  const res = await fetch(
    `${process.env.NEXT_PUBLIC_API_URL}/experience/${id}`,
  );

  return res.ok ? await res.json() : null;
};

export const readAllExperiences = async (): Promise<Experience[]> => {
  const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/experience`);
  return await res.json();
};

export const updateExperience = async (
  id: string,
  data: FieldValues,
): Promise<void> => {
  await fetch(
    `${process.env.NEXT_PUBLIC_API_URL}/experience/${id}?${qs.stringify(data)}`,
    {
      method: "PUT",
      headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
    },
  );
};

export const deleteExperience = async (id: number): Promise<void> => {
  await fetch(`${process.env.NEXT_PUBLIC_API_URL}/experience/${id}`, {
    method: "DELETE",
    headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
  });
};
