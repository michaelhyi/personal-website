import { useCallback, useState } from "react";
import { useNavigate } from "react-router-dom";

import Center from "../components/Center";
import Container from "../components/Container";
import UnauthorizedRoute from "../components/UnauthorizedRoute";
import { login } from "../services/auth";

export default function Home() {
    const navigate = useNavigate();
    const [error, setError] = useState("");

    const handleSubmit = useCallback(async (e) => {
        e.preventDefault();

        try {
            await login(e.target[0].value);
            navigate("/blog");
        } catch (err) {
            setError(err.message);
        }
    }, [navigate, setError]);

    return (
        <UnauthorizedRoute>
            <Container absoluteFooter>
                <Center className="flex flex-col items-center">
                    <form onSubmit={handleSubmit}>
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
                    </form>
                    <p className="min-h-4 mt-2 text-xs text-red-300 font-light">
                        {error || ""}
                    </p>
                </Center>
            </Container>
        </UnauthorizedRoute>
    );
}
