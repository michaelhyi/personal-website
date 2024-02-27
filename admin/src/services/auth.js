import axios from "axios";

async function extractUsernameFromGoogleToken(token) {
    const { data } = await axios(
        "https://www.googleapis.com/oauth2/v3/userinfo",
        {
            headers: { Authorization: `Bearer ${token}` },
        },
    );

    return data.email;
}

export async function login(googleToken) {
    const email = await extractUsernameFromGoogleToken(googleToken);

    const { data: jwt } = await axios.post(
        `${process.env.REACT_APP_API_URL}/v1/auth/${email}`,
    );

    localStorage.setItem("token", jwt);
}

export async function validateToken(token) {
    await axios(
        `${process.env.REACT_APP_API_URL}/v1/auth/validate-token/${token}`,
    );
}
