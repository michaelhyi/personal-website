import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { GoogleOAuthProvider } from "@react-oauth/google";
import { inject } from "@vercel/analytics";
import React from "react";
// eslint-disable-next-line react/no-deprecated
import { render } from "react-dom";
import { RouterProvider, createBrowserRouter } from "react-router-dom";

import "./index.css";
import reportWebVitals from "./reportWebVitals";
import Blog from "./pages/blog";
import Home from "./pages/home";
import Post from "./pages/post";

const router = createBrowserRouter([
    { path: "/blog", element: <Blog /> },
    { path: "/blog/post", element: <Post /> },
    { path: "/", element: <Home /> },
]);

const GOOGLE_CLIENT_ID =
    "148503656040-qu62io184ardtqdmmb6ph2ch8p466e2c.apps.googleusercontent.com";

const queryClient = new QueryClient();

render(
    <React.StrictMode>
        <QueryClientProvider client={queryClient}>
            <GoogleOAuthProvider clientId={GOOGLE_CLIENT_ID}>
                <RouterProvider router={router} />
            </GoogleOAuthProvider>
        </QueryClientProvider>
    </React.StrictMode>,
    document.getElementById("root"),
);

reportWebVitals();
inject();
