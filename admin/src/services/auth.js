import axios from "axios";

export async function extractUsernameFromGoogleToken(token) {
    const { data } = await axios(
        "https://www.googleapis.com/oauth2/v3/userinfo",
        {
            headers: { Authorization: `Bearer ${token}` },
        },
    );

    return data.email;
}

export async function login(email) {
    const { data } = await axios.post(
        `${process.env.REACT_APP_API_URL}/v1/auth/${email}`,
    );

    return data;
}

export async function validateToken(token) {
    await axios(
        `${process.env.REACT_APP_API_URL}/v1/auth/validate-token/${token}`,
    );
}
