/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./pages/**/*.{js,ts,jsx,tsx}",
    "./components/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      fontFamily: {
        main: ["Poppins"],
      },
      screens: {
        sm: "320px",
        md: "640px",
        lg: "768px",
        xl: "1152px",
      },
    },
  },
  plugins: [],
};
