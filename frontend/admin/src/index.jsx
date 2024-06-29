import "./index.css";

import React from "react";
import ReactDOM from "react-dom/client";
import { RouterProvider, createBrowserRouter } from "react-router-dom";

import { inject } from "@vercel/analytics";

import { Blog, Home, Post } from "./pages";

const router = createBrowserRouter([
    { path: "/blog", element: <Blog /> },
    { path: "/", element: <Home /> },
    { path: "/blog/post", element: <Post /> },
]);
const root = ReactDOM.createRoot(document.getElementById("root"));

root.render(
    <React.StrictMode>
        <RouterProvider router={router} />
    </React.StrictMode>,
);

inject();
