import { useGoogleLogin } from "@react-oauth/google";
import { Toaster, toast } from "react-hot-toast";
import { FcGoogle } from "react-icons/fc";
import { useNavigate } from "react-router-dom";
import Center from "../components/Center";
import Container from "../components/Container";
import Hoverable from "../components/Hoverable";
import Toast from "../components/Toast";
import UnauthorizedRoute from "../components/UnauthorizedRoute";
import { login } from "../services/auth";

export default function Home() {
    const navigate = useNavigate();

    const handleLogin = useGoogleLogin({
        // eslint-disable-next-line camelcase -- google api uses snake_case
        onSuccess: ({ access_token }) => {
            (async () => {
                try {
                    await login(access_token);
                    navigate("/blog");
                } catch (e) {
                    toast.custom(({ visible }) => (
                        <Toast
                            visible={visible}
                            message={e.response.data}
                            success={false}
                        />
                    ));
                }
            })();
        },
    });

    return (
        <UnauthorizedRoute>
            <Container absoluteFooter>
                <Center>
                    <div className="flex flex-col items-center">
                        <img
                            src="/michael.png"
                            alt="michael"
                            className="h-[100px] w-[100px] rounded-full"
                        />
                        <div className="mt-4 text-2xl font-medium">
                            Michael Yi
                        </div>
                        <div className="mt-1 text-xs font-light text-neutral-400">
                            Personal Website Admin
                        </div>
                        <Hoverable>
                            <button
                                type="button"
                                label="google login button"
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
        </UnauthorizedRoute>
    );
}
