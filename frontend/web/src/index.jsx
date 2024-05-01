import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { inject } from "@vercel/analytics";
import React from "react";
// eslint-disable-next-line react/no-deprecated
import { render } from "react-dom";
import { RouterProvider, createBrowserRouter } from "react-router-dom";
import "./index.css";
import reportWebVitals from "./reportWebVitals";

import About from "./pages/about";
import Blog from "./pages/blog";
import Home from "./pages/home";
import Lauren from "./pages/lauren";
import Portfolio from "./pages/portfolio";
import ViewPost from "./pages/view-post";
import NotFound from "./components/NotFound";

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

const queryClient = new QueryClient();

render(
    <React.StrictMode>
        <QueryClientProvider client={queryClient}>
            <RouterProvider router={router} />
        </QueryClientProvider>
    </React.StrictMode>,
    document.getElementById("root"),
);

reportWebVitals();
inject();
