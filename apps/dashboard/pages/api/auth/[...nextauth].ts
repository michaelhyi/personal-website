import axios from "axios";
import NextAuth, { type AuthOptions } from "next-auth";
import CredentialsProvider from "next-auth/providers/credentials";
import type User from "@/types/user";

export const authOptions: AuthOptions = {
  providers: [
    CredentialsProvider({
      name: "credentials",
      credentials: {
        email: { label: "email", type: "text" },
        password: { label: "password", type: "password" },
      },

      //@ts-expect-error next-auth bug. CredentialsProvider type is wrong
      authorize: async (credentials, _req) => {
        await axios(
          `${process.env.NEXT_PUBLIC_API_URL}/user/${credentials?.email}`,
        );

        return {
          name: null,
          email: credentials?.email,
          image: null,
        };
      },
    }),
  ],
  pages: {
    signIn: "/",
  },
  debug: process.env.NODE_ENV === "development",
  session: {
    strategy: "jwt",
  },
  secret: process.env.NEXTAUTH_SECRET,
};

export default NextAuth(authOptions);
