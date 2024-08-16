export async function login(password) {
    const res = await fetch(`${process.env.REACT_APP_API_URL}/auth/login`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ password }),
    });
    const { token, error } = await res.json();

    if (!res.ok) {
        throw new Error(error);
    }

    localStorage.setItem("token", token);
}

export async function validateToken() {
    const res = await fetch(
        `${process.env.REACT_APP_API_URL}/auth/validate-token`,
        {
            method: "POST",
            headers: {
                Authorization: `Bearer ${localStorage.getItem("token")}`,
            },
        },
    );

    if (!res.ok) {
        const { error } = await res.json();
        throw new Error(error);
    }
}
