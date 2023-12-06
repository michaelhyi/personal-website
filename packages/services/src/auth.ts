import axios from "axios";

export const authenticate = async (email: string): Promise<string> => {
  const res = await axios.post(
    `${process.env.NEXT_PUBLIC_API_URL}/auth/${email}`,
  );

  return res.data;
};
