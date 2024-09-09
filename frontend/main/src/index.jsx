import { inject } from "@vercel/analytics";
import React from "react";
import ReactDOM from "react-dom/client";
import { RouterProvider, createBrowserRouter } from "react-router-dom";
import "./index.css";
import About from "./pages/about";
import Blog from "./pages/blog";
import Home from "./pages/home";
import Lauren from "./pages/lauren";
import NotFound from "./pages/not-found";
import Portfolio from "./pages/portfolio";
import ReadPost from "./pages/read-post";

const router = createBrowserRouter([
    {
        path: "/",
        element: <Home />,
    },
    {
        path: "/about",
        element: <About />,
    },
    {
        path: "/blog",
        element: <Blog />,
    },
    {
        path: "/blog/:id",
        element: <ReadPost />,
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
