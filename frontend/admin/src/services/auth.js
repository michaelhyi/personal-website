import axios from "axios";

import authConfig from "./authConfig";

export async function login(password) {
    const { data } = await axios.post(
        `${process.env.REACT_APP_API_URL}/auth/login`,
        { password },
    );

    localStorage.setItem("token", data);
}

export async function validateToken() {
    await axios(
        `${process.env.REACT_APP_API_URL}/auth/validate-token`,
        authConfig(),
    );
}
