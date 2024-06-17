import { useCallback, useState } from "react";
import { useNavigate } from "react-router-dom";

import Center from "../components/Center";
import Container from "../components/Container";
import Toast from "../components/Toast";
import UnauthorizedRoute from "../components/UnauthorizedRoute";
import { login } from "../services/auth";

export default function Home() {
    const navigate = useNavigate();
    const [toast, setToast] = useState({
        message: "",
        visible: false,
        animation: "animate-fadeIn",
    });

    const handleSubmit = useCallback(async (e) => {
        e.preventDefault();

        try {
            await login(e.target[0].value);
            navigate("/blog");
        } catch (error) {
            setToast({
                message:
                    error.response && error.response.data
                        ? error.response.data
                        : "Internal server error",
                visible: true,
                animation: "animate-fadeIn",
            });

            setTimeout(() => {
                setToast({
                    visible: true,
                    message: "Internal server error",
                    animation: "animate-fadeOut",
                });
            }, 3000);

            setTimeout(() => {
                setToast({
                    message: "",
                    visible: false,
                    animation: "animate-fadeIn",
                });
            }, 4000);
        }
    }, []);

    return (
        <UnauthorizedRoute>
            <Container absoluteFooter>
                <Center className="flex flex-col items-center">
                    <img
                        src="/michael.png"
                        alt="michael"
                        className="h-[100px] w-[100px] rounded-full"
                    />
                    <h2 className="mt-4 text-2xl font-medium">Michael Yi</h2>
                    <p className="mt-1 text-xs font-light text-neutral-400">
                        Personal Website Admin
                    </p>
                    <form onSubmit={handleSubmit}>
                        <fieldset disabled={toast.visible}>
                            <input
                                type="password"
                                className="mt-4
                                   px-3
                                   py-1
                                   w-40
                                   bg-neutral-900
                                   outline-none
                                   border-[1px]
                                   border-neutral-600
                                   rounded-md
                                   text-white
                                   text-xs
                                   placeholder:text-neutral-600
                                   placeholder:text-xs"
                                placeholder="Password"
                            />
                        </fieldset>
                    </form>
                </Center>
                {toast.visible && (
                    <Toast
                        animation={toast.animation}
                        message={toast.message}
                        success={false}
                    />
                )}
            </Container>
        </UnauthorizedRoute>
    );
}
