/* eslint-disable @typescript-eslint/no-unsafe-member-access -- errors are type AxiosError */
/* eslint-disable @typescript-eslint/no-unsafe-argument -- errors are type AxiosError */
/* eslint-disable @typescript-eslint/no-non-null-assertion -- env variables will always be non-null */

import axios from "axios";
import NextAuth, { type AuthOptions } from "next-auth";
import GoogleProvider from "next-auth/providers/google";

export const authOptions: AuthOptions = {
  providers: [
    GoogleProvider({
      clientId: process.env.GOOGLE_CLIENT_ID!,
      clientSecret: process.env.GOOGLE_CLIENT_SECRET!,
    }),
  ],
  callbacks: {
    async signIn({ user }) {
      if (user.email) {
        try {
          await axios.post(
            `${process.env.NEXT_PUBLIC_API_URL}/auth/${user.email}`,
          );

          return true;
        } catch (e) {
          // @ts-expect-error -- errors are type AxiosError
          throw new Error(e.response.data);
        }
      }
      return false;
    },
  },
  pages: {
    signIn: "/",
    error: "/",
  },
  debug: process.env.NODE_ENV === "development",
  session: {
    strategy: "jwt",
    maxAge: 24 * 60 * 60 * 7,
  },
  secret: process.env.NEXTAUTH_SECRET,
};

export default NextAuth(authOptions);
