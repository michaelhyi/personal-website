package health

import (
	"encoding/json"
	"net/http"
)

type HealthController struct {
	Service *HealthService
}

func (c *HealthController) Check(w http.ResponseWriter, r *http.Request) {
	res := c.Service.Check()
	b, err := json.Marshal(res)

	if err != nil {
		w.WriteHeader(http.StatusInternalServerError)
		w.Write([]byte("Internal server error"))
		return
	}

	w.Header().Set("Content-Type", "application/json")
	w.Write([]byte(b))
}
