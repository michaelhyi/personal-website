import { http } from "./http";

export const login = async (data: { email: string; password: string }) => {
   return await http("/auth/login", "POST", data);
};

export const readUserByToken = async (token: string | null) => {
   return await http("/user/" + token, "GET");
};
