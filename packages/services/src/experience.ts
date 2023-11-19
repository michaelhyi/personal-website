import axios from "axios";
import type { FieldValues } from "react-hook-form";
import type { Experience } from "types";
import qs from "query-string";

export const createExperience = async (data: FieldValues): Promise<number> => {
  const res = await axios.post(
    `${process.env.NEXT_PUBLIC_API_URL}/experience`,
    data,
    {
      headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
    },
  );

  return res.data;
};

export const readExperience = async (
  id: string,
): Promise<Experience | null> => {
  const res = await axios(
    `${process.env.NEXT_PUBLIC_API_URL}/experience/${id}`,
  );

  return res.status === 200 ? res.data : null;
};

export const readAllExperiences = async (): Promise<Experience[]> => {
  const res = await axios(`${process.env.NEXT_PUBLIC_API_URL}/experience`);
  return res.data;
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
  await axios.delete(`${process.env.NEXT_PUBLIC_API_URL}/experience/${id}`, {
    headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
  });
};
