/** @type {import('tailwindcss').Config} */
module.exports = {
    content: ["./src/**/*.{js,jsx,ts,tsx}"],
    theme: {
        extend: {
            animation: {
                fadeIn: "fadeIn 1s ease-in-out",
                fadeOut: "fadeOut 1s ease-in-out",
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
