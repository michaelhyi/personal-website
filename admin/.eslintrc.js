module.exports = {
    env: {
        browser: true,
        es2021: true,
    },
    extends: ["airbnb", "prettier"],
    overrides: [
        {
            env: {
                node: true,
            },
            files: [".eslintrc.{js,cjs}"],
            parserOptions: {
                sourceType: "script",
            },
        },
    ],
    parserOptions: {
        ecmaVersion: "latest",
        sourceType: "module",
    },
    rules: {
        quotes: ["error", "double"],
        "react/jsx-props-no-spreading": "off",
        "react/prop-types": "off",
        "react/react-in-jsx-scope": "off",
    },
    plugins: ["prettier"],
};
