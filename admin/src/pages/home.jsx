import { useGoogleLogin } from "@react-oauth/google";
import axios from "axios";
import { useEffect, useState } from "react";
import { Toaster, toast } from "react-hot-toast";
import { FcGoogle } from "react-icons/fc";
import { useNavigate } from "react-router-dom";
import Center from "../components/Center";
import Container from "../components/Container";
import Hoverable from "../components/Hoverable";
import Loading from "../components/Loading";
import Toast from "../components/Toast";
import { login, validateToken } from "../services/auth";

export default function Home() {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);

  const handleLogin = useGoogleLogin({
    onSuccess: async ({ access_token }) => {
      const { data } = await axios(
        "https://www.googleapis.com/oauth2/v3/userinfo",
        {
          headers: { Authorization: `Bearer ${access_token}` },
        }
      );

      localStorage.setItem("token", await login(data.email));
      navigate("/blog");
    },
    onError: (error) => {
      toast.custom(({ visible }) => (
        <Toast
          visible={visible}
          message={error.error.message}
          success={false}
        />
      ));
    },
  });

  useEffect(() => {
    (async () => {
      let token = localStorage.getItem("token");

      if (!token) {
        setLoading(false);
      } else {
        try {
          await validateToken(token);
          navigate("/blog");
        } catch {
          localStorage.removeItem("token");
          setLoading(false);
        }
      }
    })();
  }, [navigate]);

  if (loading) return <Loading />;

  return (
    <Container absoluteFooter>
      <Center>
        <div className="flex flex-col items-center">
          <img
            src="/michael.png"
            alt="michael"
            className="h-[100px] w-[100px] rounded-full"
          />
          <div className="mt-4 text-2xl font-medium">Michael Yi</div>
          <div className="mt-1 text-xs font-light text-neutral-400">
            Personal Website Admin
          </div>
          <Hoverable>
            <button
              onClick={handleLogin}
              className="flex 
                         bg-neutral-800
                         items-center
                         focus:outline-none
                         text-sm 
                         font-semibold 
                         shadow-sm
                         rounded-md 
                         mt-4 
                         gap-3
                         px-6 
                         py-2"
            >
              <FcGoogle />
            </button>
          </Hoverable>
        </div>
      </Center>
      <Toaster position="top-center" />
    </Container>
  );
}
