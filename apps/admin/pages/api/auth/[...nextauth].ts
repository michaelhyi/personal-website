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
    async session({ session }) {
      if (new Date(session.expires).getTime() < Date.now()) {
        await fetch("/api/auth/signout?callbackUrl=/api/auth/session", {
          method: "POST",
          headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
          },
          body: await fetch("/api/auth/csrf").then((rs) => rs.text()),
        });
      }

      return session;
    },
    async signIn({ user }) {
      if (user.email) {
        try {
          await axios.post(
            `${process.env.NEXT_PUBLIC_API_URL}/auth/${user.email}`
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
    maxAge: 604800,
  },
  secret: process.env.NEXTAUTH_SECRET,
};

export default NextAuth(authOptions);
