import { Spinner } from "@chakra-ui/react";
import { useRouter } from "next/navigation";
import { useCallback, useEffect, useState } from "react";
import { FieldValues, SubmitHandler, useForm } from "react-hook-form";
import Error from "../components/Error";
import Footer from "../components/Footer";
import Loading from "../components/Loading";
import { login, readUserByToken } from "../services/api";

const Login = () => {
  const router = useRouter();
  const [loading, setLoading] = useState(true);
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

      await login(data as { email: string; password: string }).then(
        async (res) => {
          if (res.status === 200) {
            setError({ email: "", password: "" });

            const token: any = await res.text();
            await localStorage.setItem("token", token);

            router.push("/");
          } else if (res.status === 404) {
            setError({ email: "Email does not exist.", password: "" });
            setSubmitting(false);
          } else if (res.status === 401) {
            setError({ email: "", password: "Wrong password." });
            setSubmitting(false);
          }
        }
      );
    },
    [setSubmitting, router, setError]
  );

  useEffect(() => {
    const token = localStorage.getItem("token");

    readUserByToken(token).then((res) => {
      if (res.status === 200) router.push("/");
      else setLoading(false);
    });
  }, [router]);

  if (loading) return <Loading />;

  return (
    <div className="h-screen">
      <div className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 text-xs font-light">
        <div className="w-48">
          <div>Email</div>
          <input
            className="border-b-2 w-full mt-2"
            {...register("email")}
            id="email"
            disabled={submitting}
          />
          {error.email.length > 0 && <Error text={error.email} />}
          <div className="mt-6">Password</div>
          <input
            className="border-b-2 w-full mt-2"
            {...register("password")}
            id="password"
            disabled={submitting}
            type="password"
          />
          {error.password.length > 0 && <Error text={error.password} />}
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
