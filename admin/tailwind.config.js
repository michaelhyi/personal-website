/** @type {import('tailwindcss').Config} */
module.exports = {
    content: ["./src/**/*.{js,jsx,ts,tsx}"],
    theme: {
        extend: {
            screens: {
                sm: "320px",
            },
        },
    },
    // eslint-disable-next-line global-require -- must use require()
    plugins: [require("@tailwindcss/typography")],
};
