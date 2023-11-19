import NextAuth, { type AuthOptions } from "next-auth";
import Provider from "next-auth/providers/credentials";

export const authOptions: AuthOptions = {
  providers: [
    Provider({
      name: "credentials",
      credentials: {
        email: { label: "email", type: "text" },
        password: { label: "password", type: "password" },
      },

      //@ts-expect-error next-auth bug. CredentialsProvider type is wrong
      authorize: async (credentials, _req) => {
        const res = await fetch(
          `${process.env.NEXT_PUBLIC_API_URL}/user/${credentials?.email}`,
        );

        if (res.ok) {
          return {
            name: null,
            email: credentials?.email,
            image: null,
          };
        }

        throw new Error();
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
