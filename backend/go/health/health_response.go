package health

type HealthResponse struct {
    Status string `json:"status"`
    Uptime string `json:"uptime"`
    Details Details `json:"details"`
}

type Details struct {
    Mysql string `json:"mysql"`
    Redis string `json:"redis"`
}
