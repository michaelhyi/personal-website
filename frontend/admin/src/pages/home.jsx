import { useCallback } from "react";
import { toast, Toaster } from "react-hot-toast";
import { useNavigate } from "react-router-dom";

import Center from "../components/Center";
import Container from "../components/Container";
import Toast from "../components/Toast";
import UnauthorizedRoute from "../components/UnauthorizedRoute";
import { login } from "../services/auth";

export default function Home() {
    const navigate = useNavigate();

    const handleSubmit = useCallback(async (e) => {
        e.preventDefault();

        try {
            await login(e.target[0].value);
            navigate("/blog");
        } catch (error) {
            toast.custom(({ visible }) => (
                <Toast
                    visible={visible}
                    message={
                        error.response && error.response.data
                            ? error.response.data
                            : "Internal server error"
                    }
                    success={false}
                />
            ));
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
                        <input
                            type="password"
                            className="mt-4
                                   px-3
                                   py-1
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
                    </form>
                </Center>
                <Toaster position="top-center" />
            </Container>
        </UnauthorizedRoute>
    );
}
