import React from "react";
import ReactDOM from "react-dom/client";
import { RouterProvider, createBrowserRouter } from "react-router-dom";
import "./index.css";
import Blog from "./pages/blog";
import Home from "./pages/home";
import Post from "./pages/post";

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