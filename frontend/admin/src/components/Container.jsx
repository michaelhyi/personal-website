import "../css/container.css";

import { useEffect, useState } from "react";

import Footer from "./Footer";

export default function Container({
    children,
    absoluteFooter = false,
    footer = true,
}) {
    const [mounted, setMounted] = useState(false);

    useEffect(() => {
        setMounted(true);
    }, []);

    if (!mounted) return null;

    return (
        <main className="container-bg">
            <div className="container-content">
                {children}
                {footer ? <Footer absolute={absoluteFooter} /> : null}
            </div>
        </main>
    );
}
