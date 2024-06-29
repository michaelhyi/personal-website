import "./Container.css";

import { useEffect, useState } from "react";
import Footer from "../Footer/Footer";

export default function Container({ children, absoluteFooter = false }) {
    const [mounted, setMounted] = useState(false);

    useEffect(() => {
        setMounted(true);
    }, []);

    if (!mounted) return null;

    return (
        <main className="container-bg">
            <div className="container-content">
                {children}
                <Footer absolute={absoluteFooter} />
            </div>
        </main>
    );
}
