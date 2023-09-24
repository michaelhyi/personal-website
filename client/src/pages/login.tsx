import { useRouter } from "next/navigation";
import { useCallback, useState } from "react";
import { FieldValues, SubmitHandler, useForm } from "react-hook-form";
import Footer from "../components/Footer";
import { login } from "../services/api";
import { Spinner } from "@chakra-ui/react";

const Login = () => {
  const router = useRouter();
  const [submitting, setSubmitting] = useState(false);
  const { register, handleSubmit } = useForm<FieldValues>({
    defaultValues: {
      email: "",
      password: "",
    },
  });

  const handleLogin: SubmitHandler<FieldValues> = useCallback(
    async (data) => {
      setSubmitting(true);
      await login(data as { email: string; password: string })
        .then(async (res) => {
          console.log(res);
          await localStorage.setItem("token", res.token);
          router.push("/");
        })
        .catch()
        .finally(() => setSubmitting(false));
    },
    [setSubmitting, router]
  );

  return (
    <div className="h-full">
      <div className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 text-xs font-light">
        <div className="w-48">
          <div>Email</div>
          <input
            className="border-b-2 w-full mt-2"
            {...register("email")}
            id="email"
            disabled={submitting}
          />
          <div className="mt-6">Password</div>
          <input
            className="border-b-2 w-full mt-2"
            {...register("password")}
            id="password"
            disabled={submitting}
            type="password"
          />
          <button
            onClick={(e) => handleSubmit(handleLogin)(e)}
            className="text-center w-full py-3 bg-pink-300 text-white mt-8 duration-500 hover:opacity-50"
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

export default Login;
