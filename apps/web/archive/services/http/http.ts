const authorizedRequests = [
  { route: "/post", method: "GET" },
  { route: "/auth", method: "*" },
  { route: "/user", method: "*" },
];

const authorized = (route: string, method: string): boolean => {
  for (let i = 0; i < authorizedRequests.length; i++) {
    const request = authorizedRequests[i];

    if (request.route === route) {
      if (request.method === "*") return true;
      else if (request.method === method) return true;
    }
  }

  return false;
};

export const http = async (route: string, method: string, body?: any) => {
  const token = localStorage.getItem("token");

  return await fetch(process.env.NEXT_PUBLIC_API_URL + route, {
    body: method === "POST" ? JSON.stringify(body) : null,
    method,
    headers: {
      "Content-Type": method === "POST" ? "application/json" : "",
      Authorization: authorized(route, method) ? "" : `Bearer ${token}`,
    },
  });
};
