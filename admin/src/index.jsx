import { GoogleOAuthProvider } from "@react-oauth/google";
import { inject } from "@vercel/analytics";
import React from "react";
import ReactDOM from "react-dom/client";
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

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
    <React.StrictMode>
        <GoogleOAuthProvider clientId={GOOGLE_CLIENT_ID}>
            <RouterProvider router={router} />
        </GoogleOAuthProvider>
    </React.StrictMode>,
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
inject();
