/** @type {import('tailwindcss').Config} */
module.exports = {
    content: ["./src/**/*.{js,jsx,ts,tsx}"],
    theme: {
        extend: {
            animation: {
                fadeIn: "fadeIn 0.75s ease-in",
                fadeOut: "fadeOut 0.75s ease-out",
                popIn: "fadeIn 0.5s ease-in",
                popOut: "fadeOut 0.5s ease-out",
            },
            keyframes: {
                fadeIn: {
                    "0%": { opacity: 0 },
                    "100%": { opacity: 1 },
                },
                fadeOut: {
                    "0%": { opacity: 1 },
                    "100%": { opacity: 0 },
                },
            },
            screens: {
                sm: "320px",
            },
        },
    },
    // eslint-disable-next-line global-require -- must use require()
    plugins: [require("@tailwindcss/typography")],
};
