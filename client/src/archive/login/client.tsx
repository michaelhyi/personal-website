"use client";

import Error from "@/archive/components/Error";
import Footer from "@/components/Footer";
import { login } from "@/services/auth";
import { Spinner } from "@chakra-ui/react";
import { useRouter } from "next/navigation";
import { FC, useCallback, useEffect, useState } from "react";
import { FieldValues, SubmitHandler, useForm } from "react-hook-form";

interface Props {
   authenticated: boolean;
}

const LoginClient: FC<Props> = ({ authenticated }) => {
   const router = useRouter();
   const [submitting, setSubmitting] = useState(false);
   const [error, setError] = useState({
      email: "",
      password: "",
   });
   const { register, handleSubmit } = useForm<FieldValues>({
      defaultValues: {
         email: "",
         password: "",
      },
   });

   const handleLogin: SubmitHandler<FieldValues> = useCallback(
      async (data) => {
         setSubmitting(true);
         const res = await login(data as { email: string; password: string });

         if (res.status === 200) {
            setError({ email: "", password: "" });
            const token = await res.text();
            await localStorage.setItem("token", token);
            router.push("/");
         } else if (res.status === 404) {
            setError({ email: "Email does not exist.", password: "" });
            setSubmitting(false);
         } else if (res.status === 401) {
            setError({ email: "", password: "Wrong password." });
            setSubmitting(false);
         }
      },
      [setSubmitting, router, setError]
   );

   useEffect(() => {
      if (authenticated) router.push("/");
   }, [authenticated, router]);

   return (
      <div className="h-screen">
         <div className="absolute left-1/2 top-1/2 -translate-x-1/2 -translate-y-1/2 transform text-xs font-light">
            <div className="w-48">
               <div>Email</div>
               <input
                  className="mt-2 w-full border-b-2"
                  {...register("email")}
                  id="email"
                  disabled={submitting}
               />
               {error.email.length > 0 && <Error text={error.email} />}
               <div className="mt-6">Password</div>
               <input
                  className="mt-2 w-full border-b-2"
                  {...register("password")}
                  id="password"
                  disabled={submitting}
                  type="password"
               />
               {error.password.length > 0 && <Error text={error.password} />}
               <button
                  disabled={submitting}
                  onClick={(e) => handleSubmit(handleLogin)(e)}
                  className="mt-8 w-full bg-pink-300 py-3 text-center text-white duration-500 hover:opacity-50"
               >
                  {submitting ? <Spinner size="xs" /> : "Login"}
               </button>
            </div>
         </div>
         <div className="absolute bottom-0 left-0 right-0">
            <Footer />
         </div>
      </div>
   );
};

export default LoginClient;
