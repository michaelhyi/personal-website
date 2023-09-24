export const http = async (url: string, method: string, body?: any) => {
  return await fetch(process.env.NEXT_PUBLIC_API_URL + url, {
    body: method === "POST" ? JSON.stringify(body) : null,
    method,
    headers: {
      "Content-Type": method === "POST" ? "application/json" : "",
      Authorization:
        (url.includes("/post") && method === "GET") || url.includes("/auth")
          ? ""
          : `Bearer ${localStorage.getItem("token")}`,
    },
  });
};
