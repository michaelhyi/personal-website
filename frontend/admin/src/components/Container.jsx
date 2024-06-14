import { useEffect, useState } from "react";
import Footer from "./Footer";

export default function Container({ children, absoluteFooter = false }) {
    const [mounted, setMounted] = useState(false);

    useEffect(() => {
        setMounted(true);
    }, []);

    if (!mounted) return null;

    return (
        <main className="bg-neutral-900 text-white min-h-screen">
            <div
                className="animate-fadeIn flex flex-col pt-20 mx-auto sm:w-[360px] md:w-[480px] lg:w-[512px] xl:w-[768px]"
            >
                {children}
                <Footer absolute={absoluteFooter} />
            </div>
        </main>
    );
}
