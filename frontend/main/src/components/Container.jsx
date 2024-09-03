import "../css/container.css";

import { useEffect, useState } from "react";
import Footer from "./Footer";

export default function Container({ children, absoluteFooter = false }) {
    const [mounted, setMounted] = useState(false);

    useEffect(() => {
        setMounted(true);
    }, []);

    if (!mounted) return null;

    return (
        <main className="container">
            <div className="content">
                {children}
                <Footer absolute={absoluteFooter} />
            </div>
        </main>
    );
}
