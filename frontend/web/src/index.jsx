import "./index.css";

import React from "react";
// eslint-disable-next-line react/no-deprecated
import { render } from "react-dom";
import { RouterProvider, createBrowserRouter } from "react-router-dom";

import { inject } from "@vercel/analytics";

import { About, Blog, Home, Lauren, Portfolio, ViewPost } from "./pages";
import { NotFound } from "./components";

const router = createBrowserRouter([
    {
        path: "/about",
        element: <About />,
    },
    {
        path: "/blog",
        element: <Blog />,
    },
    {
        path: "/",
        element: <Home />,
    },
    {
        path: "/lauren",
        element: <Lauren />,
    },
    {
        path: "/portfolio",
        element: <Portfolio />,
    },
    {
        path: "/blog/:id",
        element: <ViewPost />,
    },
    {
        path: "*",
        element: <NotFound />,
    },
]);

render(
    <React.StrictMode>
        <RouterProvider router={router} />
    </React.StrictMode>,
    document.getElementById("root"),
);

inject();
