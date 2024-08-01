package health

import (
	"fmt"
	"time"
)

var MINUTES_PER_HOUR = 60
var SECONDS_PER_MINUTE = 60
var MS_PER_SECOND = 1000

type HealthService struct {
	StartTime time.Time
}

func (s *HealthService) Check() HealthResponse {
	timestamp := time.Now()
	uptime := timestamp.Sub(s.StartTime)

	hrs := int(uptime.Hours())
	mins := int(uptime.Minutes()) % MINUTES_PER_HOUR
	seconds := int(uptime.Seconds()) % SECONDS_PER_MINUTE
	ms := int(uptime.Milliseconds()) % MS_PER_SECOND
	formattedUptime := fmt.Sprintf("%02dh %02dm %02ds %dms", hrs, mins, seconds, ms)

	return HealthResponse{
		Status: UP,
		Uptime: formattedUptime,
		Details: Details{
			Mysql: UP,
			Redis: UP,
		},
	}
}
