import axios from "axios";

export const authenticate = async (email: string): Promise<string> => {
  const { data } = await axios.post(
    `${process.env.NEXT_PUBLIC_API_URL}/auth/${email}`,
  );

  return data;
};
