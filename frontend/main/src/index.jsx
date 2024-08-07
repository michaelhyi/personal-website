import "./index.css";

import React from "react";
import ReactDOM from "react-dom/client";
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
const root = ReactDOM.createRoot(document.getElementById("root"));

root.render(
    <React.StrictMode>
        <RouterProvider router={router} />
    </React.StrictMode>,
);

inject();
