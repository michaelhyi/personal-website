import "./index.css";
import React from "react";
// eslint-disable-next-line react/no-deprecated
import { render } from "react-dom";
import { RouterProvider, createBrowserRouter } from "react-router-dom";

import { inject } from "@vercel/analytics";

import { Blog, Home, Post } from "./pages";

const router = createBrowserRouter([
    { path: "/blog", element: <Blog /> },
    { path: "/", element: <Home /> },
    { path: "/blog/post", element: <Post /> },
]);

render(
    <React.StrictMode>
        <RouterProvider router={router} />
    </React.StrictMode>,
    document.getElementById("root"),
);

inject();
