export async function login(password) {
    const res = await fetch(`${process.env.REACT_APP_API_URL}/auth/login`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ password }),
    });

    if (!res.ok) {
        throw new Error(await res.text());
    }

    localStorage.setItem("token", await res.text());
}

export async function validateToken() {
    const res = await fetch(
        `${process.env.REACT_APP_API_URL}/auth/validate-token`,
        {
            headers: {
                Authorization: `Bearer ${localStorage.getItem("token")}`,
            },
        },
    );

    if (!res.ok) {
        throw new Error(await res.text());
    }
}
