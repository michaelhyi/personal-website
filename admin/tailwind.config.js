/** @type {import('tailwindcss').Config} */
module.exports = {
    content: ["./src/**/*.{js,jsx,ts,tsx}"],
    theme: {
        extend: {
            animation: {
                enter: "enter 300ms ease-out",
                leave: "leave 300ms ease-in forwards",
            },
            keyframes: {
                enter: {
                    "0%": { transform: "scale(0.9)", opacity: 0 },
                    "100%": { transform: "scale(1)", opacity: 1 },
                },
                leave: {
                    "0%": { transform: "scale(1)", opacity: 1 },
                    "100%": { transform: "scale(0.9)", opacity: 0 },
                },
            },
            screens: {
                sm: "320px",
            },
        },
    },
    // eslint-disable-next-line global-require
    plugins: [require("@tailwindcss/typography")],
};
