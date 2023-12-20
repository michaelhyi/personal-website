import axios from "axios";

export const authenticate = async (email: string): Promise<string> => {
  const { data } = await axios.post(
    `${process.env.NEXT_PUBLIC_API_URL}/auth/${email}`,
  );

  return data;
};

export const validateToken = async (token: string): Promise<boolean> => {
  const { data } = await axios(
    `${process.env.NEXT_PUBLIC_API_URL}/auth/validate-token/${token}`,
  );

  return data;
};
